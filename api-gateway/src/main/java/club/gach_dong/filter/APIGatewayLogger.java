package club.gach_dong.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

@Slf4j
@Component
public class APIGatewayLogger implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        log.error("[API-GATEWAY] 일단 요청 들어옴: " + request.getURI().getPath());
        // 기존 정보 수집
        String userAgent = request.getHeaders().getFirst("User-Agent");
        String proxyIp = request.getHeaders().getFirst("X-Forwarded-For");
        InetSocketAddress address = request.getRemoteAddress();
        String originIp = proxyIp != null ? proxyIp : (address != null ? address.toString() : "UNKNOWN SOURCE");
        String fullPath = request.getURI().getPath() + (request.getURI().getQuery() != null ? "?" + request.getURI().getQuery() : "");

        // Route 정보 수집
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String routeId = route != null ? route.getId() : "UNKNOWN_ROUTE";
        String routeUri = route != null ? route.getUri().toString() : "UNKNOWN_URI";
        String requestId = request.getId();

        return chain.filter(exchange).doOnSuccess(resVoid -> {
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("[API-GATEWAY] RequestId: {}, Method: {}, Path: {}, OriginIP: {}, Status: {}, ExecutionTime: {}ms, UserAgent: {}, RouteId: {}, RouteUri: {}",
                    requestId, request.getMethod(), fullPath, originIp,
                    response.getStatusCode(), executionTime, userAgent, routeId, routeUri);
        });
    }
}