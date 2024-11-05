package club.gach_dong.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import club.gach_dong.entity.Admin;
import club.gach_dong.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private final Key userJwtKey;
    private final Key adminJwtKey;
    private final RedisTemplate<String, String> redisTemplate;

    public JwtUtil(
            @Value("${jwt.user.secret}") String userJwtSecret,
            @Value("${jwt.admin.secret}") String adminJwtSecret,
            RedisTemplate<String, String> redisTemplate) {
        this.userJwtKey = Keys.hmacShaKeyFor(userJwtSecret.getBytes(StandardCharsets.UTF_8));
        this.adminJwtKey = Keys.hmacShaKeyFor(adminJwtSecret.getBytes(StandardCharsets.UTF_8));
        this.redisTemplate = redisTemplate;
    }

    public String generateUserToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("user_id", user.getId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1일 후 만료
                .signWith(userJwtKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateAdminToken(Admin admin) {
        return Jwts.builder()
                .setSubject(admin.getEmail())
                .claim("user_id", admin.getId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1일 후 만료
                .signWith(adminJwtKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserEmailFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(userJwtKey)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 사용자 토큰입니다.");
        }
    }

    public String getAdminEmailFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(adminJwtKey)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 관리자 토큰입니다.");
        }
    }

    public boolean validateUserToken(String token) {
        if (isTokenBlacklisted(token.replace("Bearer ", ""))) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(userJwtKey).parseClaimsJws(token.replace("Bearer ", ""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateAdminToken(String token) {
        if (isTokenBlacklisted(token.replace("Bearer ", ""))) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(adminJwtKey).parseClaimsJws(token.replace("Bearer ", ""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void blacklistUserToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(userJwtKey)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        Date expirationDate = claims.getExpiration();
        Date currentDate = new Date();

        long remainingValidity = expirationDate.getTime() - currentDate.getTime();

        if (remainingValidity > 0) {
            redisTemplate.opsForValue().set(token, "blacklisted", remainingValidity, TimeUnit.MILLISECONDS);
        }
    }

    public void blacklistAdminToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(adminJwtKey)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        Date expirationDate = claims.getExpiration();
        Date currentDate = new Date();

        long remainingValidity = expirationDate.getTime() - currentDate.getTime();

        if (remainingValidity > 0) {
            redisTemplate.opsForValue().set(token, "blacklisted", remainingValidity, TimeUnit.MILLISECONDS);
        }
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(token);
    }
}
