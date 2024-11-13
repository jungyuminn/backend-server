package club.gach_dong.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import club.gach_dong.api.PublicAuthApiSpecification;
import club.gach_dong.dto.request.LoginRequest;
import club.gach_dong.dto.request.ProfilesRequest;
import club.gach_dong.dto.request.RegistrationRequest;
import club.gach_dong.dto.response.AuthResponse;
import club.gach_dong.dto.response.UserProfileResponse;
import club.gach_dong.entity.Admin;
import club.gach_dong.entity.User;
import club.gach_dong.service.AdminService;
import club.gach_dong.service.UserService;
import club.gach_dong.util.JwtUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PublicAuthController implements PublicAuthApiSpecification {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AdminService adminService;

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
    public ResponseEntity<String> sendRegistrationVerificationCode(@RequestParam String email) {
        try {
            userService.sendRegistrationVerificationCode(email);
            return ResponseEntity.ok("회원가입용 인증 코드가 이메일로 발송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드 발송 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> completeRegistration(@Valid @RequestBody RegistrationRequest registrationRequest) {
        try {
            userService.completeRegistration(registrationRequest);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.email());

        if (user == null || !userService.checkPassword(user, loginRequest.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthResponse.withMessage("이메일 또는 비밀번호가 올바르지 않습니다."));
        }

        if (!user.isEnabled()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthResponse.withMessage("계정이 활성화되지 않았습니다."));
        }

        String token = jwtUtil.generateUserToken(user);
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

    @Override
    public ResponseEntity<String> verifyCode(@RequestParam String email, @RequestParam String code) {
        try {
            userService.verifyCode(email, code);
            return ResponseEntity.ok("인증 코드 확인이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드 검증 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<UserProfileResponse>> getProfiles(
            @Valid @RequestBody ProfilesRequest profilesRequest) {

        try {
            List<UserProfileResponse> profiles = new ArrayList<>();
            for (String uuid : profilesRequest.userReferenceId()) {
                User user = userService.findByUserReferenceId(uuid);
                if (user != null) {
                    String profileImageUrl = userService.getProfileImageUrl(uuid);
                    user.setProfileImageUrl(profileImageUrl);
                    userService.updateUserProfileImage(user);
                    profiles.add(UserProfileResponse.from(user));
                } else {
                    Admin admin = adminService.findByUserReferenceId(uuid);
                    if (admin != null) {
                        String profileImageUrl = adminService.getProfileImageUrl(uuid);
                        admin.setProfileImageUrl(profileImageUrl);
                        adminService.updateAdminProfileImage(admin);
                        profiles.add(UserProfileResponse.from(admin));
                    }
                }
            }
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
