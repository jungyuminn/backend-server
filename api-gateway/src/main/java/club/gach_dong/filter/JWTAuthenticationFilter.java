package club.gach_dong.filter;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Hints;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JWTAuthenticationFilter extends AbstractGatewayFilterFactory<Object> {

    @Value("${app.jwt.verify-key}")
    private String verifyKey;

    private JwtParser jwtParser;

    private final static String USER_REFERENCE_ID_CLAIM_KEY = "user_reference_id";
    private final static String REFERENCE_ID_HEADER_KEY = "X-USER-REFERENCE-ID";

    @PostConstruct
    public void init() {
        Key signingKey = new SecretKeySpec(verifyKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
        jwtParser = Jwts.parserBuilder().setSigningKey(signingKey).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Authorization 헤더 체크
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return createErrorResponse(exchange, "AUTH0001", "Authorization 헤더가 없습니다.");
            }

            // 인증된 사용자 정보가 이미 존재하는 경우(헤더 변조 방지)
            if (request.getHeaders().containsKey(REFERENCE_ID_HEADER_KEY)) {
                return createErrorResponse(exchange, "AUTH0000", "인증 실패: 변조된 헤더");
            }

            // JWT 토큰 추출 및 검증
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String token = authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
            if (token == null) {
                return createErrorResponse(exchange, "AUTH0002", "올바르지 않은 토큰 형식입니다.");
            }

            return validateAndForwardToken(exchange, chain, token);
        };
    }

    private Mono<Void> validateAndForwardToken(ServerWebExchange exchange, GatewayFilterChain chain, String token) {
        try {
            // JWT 토큰 검증 및 클레임 추출
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            String userId = claims.get(USER_REFERENCE_ID_CLAIM_KEY, String.class);

            // 요청 헤더에 사용자 ID 추가
            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().header(REFERENCE_ID_HEADER_KEY, userId).build();
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        } catch (ExpiredJwtException e) {
            return createErrorResponse(exchange, "AUTH0003", "토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            return createErrorResponse(exchange, "AUTH0004", "올바르지 않은 토큰입니다.");
        } catch (Exception e) {
            return createErrorResponse(exchange, "AUTH0005", "인증에 실패했습니다.");
        }
    }

    private Mono<Void> createErrorResponse(ServerWebExchange exchange, String code, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> errorMap = Map.of("code", code, "message", message);

        return response.writeWith(Mono.fromSupplier(() -> new Jackson2JsonEncoder().encodeValue(errorMap,
                response.bufferFactory(),
                ResolvableType.forInstance(errorMap),
                MediaType.APPLICATION_JSON,
                Hints.from(Hints.LOG_PREFIX_HINT, exchange.getLogPrefix())
        )));
    }
}