package club.gach_dong.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.gach_dong.annotation.RequestUserReferenceId;
import club.gach_dong.api.UserApiSpecification;
import club.gach_dong.dto.request.UserProfileRequest;
import club.gach_dong.dto.response.UserProfileResponse;
import club.gach_dong.entity.User;
import club.gach_dong.exception.SuccessStatus;
import club.gach_dong.exception.UserException;
import club.gach_dong.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApiSpecification {
    private final UserService userService;

    @Override
    public ResponseEntity<UserProfileResponse> uploadProfileImage(
            @Valid @ModelAttribute UserProfileRequest userProfileRequest,
            @RequestUserReferenceId String userReferenceId) {

        if (userReferenceId.isEmpty()) {
            throw new UserException.UserNotFound();
        }

        User user = userService.getOrCreateUser(userReferenceId);
        UserProfileResponse userProfileResponse = userService.saveProfileImage(userReferenceId, userProfileRequest.image());
        return ResponseEntity.ok(userProfileResponse);
    }

    @Override
    public ResponseEntity<UserProfileResponse> updateProfileImage(
            @Valid @ModelAttribute UserProfileRequest userProfileRequest,
            @RequestUserReferenceId String userReferenceId) {

        if (userReferenceId.isEmpty()) {
            throw new UserException.UserNotFound();
        }

        UserProfileResponse userProfileResponse = userService.updateProfileImage(userReferenceId, userProfileRequest.image());
        return ResponseEntity.ok(userProfileResponse);
    }

    @Override
    public ResponseEntity<String> deleteProfileImage(@RequestUserReferenceId String userReferenceId) {
        if (userReferenceId.isEmpty()) {
            throw new UserException.UserNotFound();
        }

        userService.deleteProfileImage(userReferenceId);
        return ResponseEntity.ok(SuccessStatus.IMAGE_DELETED.getMessage());
    }

    @Override
    public ResponseEntity<String> getProfileImage(@PathVariable String userReferenceId) {
        if (userReferenceId.isEmpty()) {
            throw new UserException.UserNotFound();
        }

        String profileImageUrl = userService.getProfileImage(userReferenceId);
        return ResponseEntity.ok(profileImageUrl);
    }
}
