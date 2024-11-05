package club.gach_dong.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import club.gach_dong.api.AdminAuthApiSpecification;
import club.gach_dong.dto.request.ChangePasswordRequest;
import club.gach_dong.dto.response.UserProfileResponse;
import club.gach_dong.entity.Admin;
import club.gach_dong.service.AdminService;
import club.gach_dong.util.JwtUtil;

@RestController
@RequestMapping("/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController implements AdminAuthApiSpecification {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String token, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        if (!jwtUtil.validateAdminToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        try {
            String email = jwtUtil.getAdminEmailFromToken(token);
            Admin admin = adminService.findByEmail(email);

            if (adminService.checkPassword(admin, changePasswordRequest.currentPassword())) {
                admin.setPassword(adminService.encodePassword(changePasswordRequest.newPassword()));
                adminService.updateUser(admin);
                return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("현재 비밀번호가 올바르지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            adminService.blacklistToken(jwtToken);
            return ResponseEntity.ok("로그아웃 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteAccount(@RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateAdminToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        try {
            String email = jwtUtil.getAdminEmailFromToken(token);
            adminService.deleteUser(email);
            adminService.blacklistToken(token);
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 탈퇴 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<UserProfileResponse> getProfile(@RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateAdminToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            String email = jwtUtil.getAdminEmailFromToken(token);
            Admin admin = adminService.findByEmail(email);
            if (admin == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            UserProfileResponse userProfileResponse = UserProfileResponse.from(admin);
            return ResponseEntity.ok(userProfileResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
