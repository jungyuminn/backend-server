package club.gach_dong.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import club.gach_dong.api.PublicAuthApiSpecification;
import club.gach_dong.dto.AuthResponse;
import club.gach_dong.dto.RegistrationDto;
import club.gach_dong.dto.LoginDto;
import club.gach_dong.entity.User;
import club.gach_dong.service.UserService;
import club.gach_dong.util.JwtUtil;

@RestController
@RequiredArgsConstructor
public class PublicAuthController implements PublicAuthApiSpecification {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        try {
            userService.sendVerificationCode(email);
            return ResponseEntity.ok("인증 코드가 이메일로 발송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드 발송 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> completeRegistration(@Valid @RequestBody RegistrationDto registrationDto) {
        try {
            userService.verifyCode(registrationDto.getEmail(), registrationDto.getCode());
            userService.completeRegistration(
                    registrationDto.getEmail(),
                    registrationDto.getPassword(),
                    registrationDto.getName(),
                    registrationDto.getRole()
            );
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        User user = userService.findByEmail(loginDto.getEmail());

        if (user == null || !userService.checkPassword(user, loginDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthResponse.withMessage("이메일 또는 비밀번호가 올바르지 않습니다."));
        }

        if (!user.isEnabled()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthResponse.withMessage("계정이 활성화되지 않았습니다."));
        }

        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(AuthResponse.of(token));
    }

    @Override
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String code) {
        try {
            userService.verifyCode(email, code);
            String newPassword = userService.generateRandomPassword();
            userService.resetPassword(email, newPassword);
            userService.sendResetPasswordEmail(email, newPassword);
            return ResponseEntity.ok("임시 비밀번호가 이메일로 발송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 재발급 실패: " + e.getMessage());
        }
    }
}
