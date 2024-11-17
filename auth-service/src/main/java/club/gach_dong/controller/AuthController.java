package club.gach_dong.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import club.gach_dong.api.AuthApiSpecification;
import club.gach_dong.dto.request.ChangePasswordRequest;
import club.gach_dong.dto.response.ChangeNameResponse;
import club.gach_dong.dto.response.TokenResponse;
import club.gach_dong.dto.response.UserProfileResponse;
import club.gach_dong.entity.User;
import club.gach_dong.service.UserService;
import club.gach_dong.util.JwtUtil;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApiSpecification {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String token, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        if (!jwtUtil.validateUserToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        try {
            String email = jwtUtil.getUserEmailFromToken(token);
            User user = userService.findByEmail(email);

            if (userService.checkPassword(user, changePasswordRequest.currentPassword())) {
                user.setPassword(userService.encodePassword(changePasswordRequest.newPassword()));
                userService.updateUser(user);
                return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("현재 비밀번호가 올바르지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token, @RequestHeader("Refresh-Token") String refreshToken) {
        try {
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            userService.blacklistToken(jwtToken);
            userService.blacklistUserRefreshToken(refreshToken);

            return ResponseEntity.ok("로그아웃 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃 실패: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<String> deleteAccount(@RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateUserToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        try {
            String email = jwtUtil.getUserEmailFromToken(token);
            userService.deleteUser(email);
            userService.blacklistToken(token);
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 탈퇴 실패: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<UserProfileResponse> getProfile(@RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateUserToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            String userReferenceId = jwtUtil.getUserReferenceIdFromToken(token);

            User user = userService.findByUserReferenceId(userReferenceId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            String profileImageUrl = userService.getProfileImageUrl(userReferenceId);

            user.setProfileImageUrl(profileImageUrl);
            userService.updateUserProfileImage(user);

            UserProfileResponse userProfileResponse = UserProfileResponse.from(user);
            return ResponseEntity.ok(userProfileResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    public ResponseEntity<TokenResponse> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        if (!jwtUtil.validateUserRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(TokenResponse.withMessage("유효하지 않은 Refresh Token입니다."));
        }

        try {
            String email = jwtUtil.getUserEmailFromToken(refreshToken);
            User user = userService.findByEmail(email);

            String newAccessToken = jwtUtil.generateUserToken(user);

            return ResponseEntity.ok(TokenResponse.of(newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(TokenResponse.withMessage("Access Token 재발급 실패: " + e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ChangeNameResponse> changeName(@RequestHeader("Authorization") String token, @RequestParam String newName) {
        if (!jwtUtil.validateUserToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            String email = jwtUtil.getUserEmailFromToken(token);
            User user = userService.changeUserName(email, newName);

            if (user != null) {
                return ResponseEntity.ok(ChangeNameResponse.of(user.getUserReferenceId(), newName));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
