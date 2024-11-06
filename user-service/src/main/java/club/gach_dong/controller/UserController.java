package club.gach_dong.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.gach_dong.api.UserApiSpecification;
import club.gach_dong.dto.request.UserProfileRequest;
import club.gach_dong.dto.response.UserProfileResponse;
import club.gach_dong.entity.User;
import club.gach_dong.service.UserService;
import club.gach_dong.exception.ErrorStatus;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApiSpecification {
    private final UserService userService;

    @Override
    public ResponseEntity<UserProfileResponse> uploadProfileImage(
            @Valid @ModelAttribute UserProfileRequest userProfileRequest,
            @club.gach_dong.annotation.RequestUserReferenceId String userReferenceId) {

        if (userReferenceId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            User user = userService.getOrCreateUser(userReferenceId);
            UserProfileResponse userProfileResponse = userService.saveProfileImage(userReferenceId, userProfileRequest.image());
            return ResponseEntity.ok(userProfileResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<UserProfileResponse> updateProfileImage(
            @Valid @ModelAttribute UserProfileRequest userProfileRequest,
            @club.gach_dong.annotation.RequestUserReferenceId String userReferenceId) {

        if (userReferenceId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            UserProfileResponse userProfileResponse = userService.updateProfileImage(userReferenceId, userProfileRequest.image());
            return ResponseEntity.ok(userProfileResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<String> deleteProfileImage(@club.gach_dong.annotation.RequestUserReferenceId String userReferenceId) {
        if (userReferenceId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorStatus.USER_NOT_FOUND.getMessage());
        }

        try {
            userService.deleteProfileImage(userReferenceId);
            return ResponseEntity.ok("이미지 삭제 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 삭제 실패");
        }
    }

    @Override
    public ResponseEntity<String> getProfileImage(@club.gach_dong.annotation.RequestUserReferenceId String userReferenceId) {
        if (userReferenceId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            String profileImageUrl = userService.getProfileImage(userReferenceId);
            if (profileImageUrl == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("프로필 이미지가 존재하지 않습니다.");
            }

            return ResponseEntity.ok(profileImageUrl);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 조회 실패: " + e.getMessage());
        }
    }
}
