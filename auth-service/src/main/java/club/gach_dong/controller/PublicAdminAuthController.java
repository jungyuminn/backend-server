package club.gach_dong.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import club.gach_dong.api.PublicAdminAuthApiSpecification;
import club.gach_dong.dto.request.LoginRequest;
import club.gach_dong.dto.request.RegistrationRequest;
import club.gach_dong.dto.response.AuthResponse;
import club.gach_dong.entity.Admin;
import club.gach_dong.service.AdminService;
import club.gach_dong.util.JwtUtil;

@RestController
@RequiredArgsConstructor
public class PublicAdminAuthController implements PublicAdminAuthApiSpecification {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> sendRegistrationVerificationCode(@RequestParam String email) {
        try {
            adminService.sendRegistrationVerificationCode(email);
            return ResponseEntity.ok("회원가입용 인증 코드가 이메일로 발송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입용 인증 코드 발송 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> completeRegistration(@Valid @RequestBody RegistrationRequest registrationRequest) {
        try {
            adminService.completeRegistration(registrationRequest);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Admin admin = adminService.findByEmail(loginRequest.email());

        if (admin == null || !adminService.checkPassword(admin, loginRequest.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthResponse.withMessage("이메일 또는 비밀번호가 올바르지 않습니다."));
        }

        if (!admin.isEnabled()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthResponse.withMessage("계정이 활성화되지 않았습니다."));
        }

        String token = jwtUtil.generateAdminToken(admin);
        return ResponseEntity.ok(AuthResponse.of(token));
    }


    @Override
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String code) {
        try {
            adminService.verifyCode(email, code);
            String newPassword = adminService.generateRandomPassword();
            adminService.resetPassword(email, newPassword);
            adminService.sendResetPasswordEmail(email, newPassword);
            return ResponseEntity.ok("임시 비밀번호가 이메일로 발송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 재발급 실패: " + e.getMessage());
        }
    }
}
