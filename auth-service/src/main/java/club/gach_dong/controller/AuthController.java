package club.gach_dong.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import club.gach_dong.api.AuthApiSpecification;
import club.gach_dong.dto.AuthResponse;
import club.gach_dong.dto.ChangePasswordDto;
import club.gach_dong.dto.LoginDto;
import club.gach_dong.dto.RegistrationDto;
import club.gach_dong.entity.User;
import club.gach_dong.service.UserService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApiSpecification {

    private final UserService userService;
    private final JavaMailSender mailSender;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key jwtKey;

    @PostConstruct
    public void init() {
        jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    @PostMapping("/send_verification_code")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        try {
            userService.sendVerificationCode(email);
            return ResponseEntity.ok("인증 코드가 이메일로 발송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드 발송 실패: " + e.getMessage());
        }
    }

    @Override
    @PostMapping("/signup")
    public ResponseEntity<String> completeRegistration(@Valid @RequestBody RegistrationDto registrationDto) {
        try {
            userService.verifyCode(registrationDto.getEmail(), registrationDto.getCode());
            userService.completeRegistration(registrationDto.getEmail(), registrationDto.getPassword(), registrationDto.getName(), registrationDto.getRole());
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        User user = userService.findByEmail(loginDto.getEmail());

        if (user != null && userService.checkPassword(user, loginDto.getPassword())) {
            if (!user.isEnabled()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            String token = generateToken(user);
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @Override
    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestHeader("Authorization") String token) {
        try {
            String email = getEmailFromToken(token);
            userService.resetPassword(email);
            return ResponseEntity.ok("임시 비밀번호가 이메일로 발송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 재발급 실패: " + e.getMessage());
        }
    }

    @Override
    @PostMapping("/change_password")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String token, @Valid @RequestBody ChangePasswordDto changePasswordDto) {
        try {
            String email = getEmailFromToken(token);
            User user = userService.findByEmail(email);

            if (userService.checkPassword(user, changePasswordDto.getCurrentPassword())) {
                user.setPassword(userService.encodePassword(changePasswordDto.getNewPassword()));
                userService.updateUser(user);
                return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("현재 비밀번호가 올바르지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경 실패: " + e.getMessage());
        }
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(jwtKey, SignatureAlgorithm.HS512)
                .compact();
    }

    private String getEmailFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtKey)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        } catch (io.jsonwebtoken.SignatureException e) {
            throw new RuntimeException("유효하지 않은 토큰 서명입니다.");
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }
}
