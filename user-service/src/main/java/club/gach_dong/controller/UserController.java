package club.gach_dong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import club.gach_dong.api.UserApiSpecification;
import club.gach_dong.dto.UserProfileDto;
import club.gach_dong.entity.User;
import club.gach_dong.repository.UserRepository;
import club.gach_dong.service.UserService;
import club.gach_dong.exception.ErrorStatus;
import club.gach_dong.exception.SuccessStatus;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApiSpecification {
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    @PostMapping("/{userId}/upload_profile_image")
    public ResponseEntity<UserProfileDto> uploadProfileImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("userId") String userId) {

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            User user = userRepository.findByEmail(userId)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setEmail(userId);
                        return userRepository.save(newUser);
                    });

            UserProfileDto userProfileDto = userService.saveProfileImage(userId, image);
            return ResponseEntity.ok(userProfileDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @PostMapping("/{userId}/update_profile_image")
    public ResponseEntity<UserProfileDto> updateProfileImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("userId") String userId) {

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            UserProfileDto userProfileDto = userService.updateProfileImage(userId, image);
            return ResponseEntity.ok(userProfileDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @DeleteMapping("/{userId}/delete_profile_image")
    public ResponseEntity<String> deleteProfileImage(@PathVariable("userId") String userId) {

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorStatus.USER_NOT_FOUND.getMessage());
        }

        try {
            userService.deleteProfileImage(userId);
            return ResponseEntity.ok(SuccessStatus.IMAGE_DELETED.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 삭제 실패");
        }
    }
}
