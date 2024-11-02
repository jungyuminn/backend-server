package club.gach_dong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.gach_dong.api.UserApiSpecification;
import club.gach_dong.dto.request.UserProfileRequest;
import club.gach_dong.dto.response.UserProfileResponse;
import club.gach_dong.entity.User;
import club.gach_dong.repository.UserRepository;
import club.gach_dong.service.UserService;
import club.gach_dong.exception.ErrorStatus;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApiSpecification {
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<UserProfileResponse> uploadProfileImage(
            @ModelAttribute UserProfileRequest userProfileRequest,
            HttpServletRequest httpServletRequest) {

        String userId = httpServletRequest.getHeader("X-MEMBER-ID");

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            User user = userRepository.findByEmail(userId)
                    .orElseGet(() -> {
                        User newUser = User.of(userId, null);
                        return userRepository.save(newUser);
                    });

            UserProfileResponse userProfileResponse = userService.saveProfileImage(userId, userProfileRequest.image());
            return ResponseEntity.ok(userProfileResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<UserProfileResponse> updateProfileImage(
            @ModelAttribute UserProfileRequest userProfileRequest,
            HttpServletRequest httpServletRequest) {

        String userId = httpServletRequest.getHeader("X-MEMBER-ID");

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            UserProfileResponse userProfileResponse = userService.updateProfileImage(userId, userProfileRequest.image());
            return ResponseEntity.ok(userProfileResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<String> deleteProfileImage(HttpServletRequest httpServletRequest) {
        String userId = httpServletRequest.getHeader("X-MEMBER-ID");

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorStatus.USER_NOT_FOUND.getMessage());
        }

        try {
            userService.deleteProfileImage(userId);
            return ResponseEntity.ok("이미지 삭제 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 삭제 실패");
        }
    }

    @Override
    public ResponseEntity<String> getProfileImage(HttpServletRequest httpServletRequest) {
        String userId = httpServletRequest.getHeader("X-MEMBER-ID");

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            User user = userRepository.findByEmail(userId)
                    .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_NOT_FOUND.getMessage()));

            String profileImageUrl = user.getProfileImageUrl();
            if (profileImageUrl == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("프로필 이미지가 존재하지 않습니다.");
            }

            return ResponseEntity.ok(profileImageUrl);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 조회 실패: " + e.getMessage());
        }
    }
}