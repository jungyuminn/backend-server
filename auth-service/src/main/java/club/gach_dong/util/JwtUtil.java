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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
        Date expirationDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("user_reference_id", user.getUserReferenceId())
                .setExpiration(expirationDate)
                .signWith(userJwtKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateAdminToken(Admin admin) {
        Date expirationDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return Jwts.builder()
                .setSubject(admin.getEmail())
                .claim("user_reference_id", admin.getUserReferenceId())
                .setExpiration(expirationDate)
                .signWith(adminJwtKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateUserRefreshToken(User user) {
        Date expirationDate = Date.from(Instant.now().plus(7, ChronoUnit.DAYS));
        String refreshToken = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("user_reference_id", user.getUserReferenceId())
                .setExpiration(expirationDate)
                .signWith(userJwtKey, SignatureAlgorithm.HS512)
                .compact();

        redisTemplate.opsForValue().set(refreshToken, user.getEmail(), 7, TimeUnit.DAYS);
        return refreshToken;
    }

    public String generateAdminRefreshToken(Admin admin) {
        Date expirationDate = Date.from(Instant.now().plus(7, ChronoUnit.DAYS));
        String refreshToken = Jwts.builder()
                .setSubject(admin.getEmail())
                .claim("user_reference_id", admin.getUserReferenceId())
                .setExpiration(expirationDate)
                .signWith(adminJwtKey, SignatureAlgorithm.HS512)
                .compact();

        redisTemplate.opsForValue().set(refreshToken, admin.getEmail(), 7, TimeUnit.DAYS);
        return refreshToken;
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

    public String getUserReferenceIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(userJwtKey)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            return claims.get("user_reference_id", String.class);
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

    public String getAdminReferenceIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(adminJwtKey)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            return claims.get("user_reference_id", String.class);
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
            redisTemplate.opsForValue().set("blacklist:" + token, "blacklisted", remainingValidity, TimeUnit.MILLISECONDS);
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
            redisTemplate.opsForValue().set("blacklist:" + token, "blacklisted", remainingValidity, TimeUnit.MILLISECONDS);
        }
    }

    public void blacklistUserRefreshToken(String refreshToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(userJwtKey)
                .parseClaimsJws(refreshToken.replace("Bearer ", ""))
                .getBody();

        Date expirationDate = claims.getExpiration();
        Date currentDate = new Date();

        long remainingValidity = expirationDate.getTime() - currentDate.getTime();

        if (remainingValidity > 0) {
            redisTemplate.opsForValue().set("blacklist:" + refreshToken.replace("Bearer ", ""), "blacklisted", remainingValidity, TimeUnit.MILLISECONDS);
        }
    }

    public void blacklistAdminRefreshToken(String adminRefreshToken) {
        String token = adminRefreshToken.replace("Bearer ", "");

        Claims claims = Jwts.parser()
                .setSigningKey(adminJwtKey)
                .parseClaimsJws(token)
                .getBody();

        Date expirationDate = claims.getExpiration();
        Date currentDate = new Date();

        long remainingValidity = expirationDate.getTime() - currentDate.getTime();

        if (remainingValidity > 0) {
            redisTemplate.opsForValue().set("blacklist:" + token, "blacklisted", remainingValidity, TimeUnit.MILLISECONDS);
        }
    }

    public boolean validateUserRefreshToken(String refreshToken) {
        String token = refreshToken.replace("Bearer ", "");

        if (isTokenBlacklisted(token)) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(userJwtKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateAdminRefreshToken(String adminRefreshToken) {
        String token = adminRefreshToken.replace("Bearer ", "");

        if (isTokenBlacklisted(token)) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(adminJwtKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey("blacklist:" + token);
    }
}
