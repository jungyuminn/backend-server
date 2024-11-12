package club.gach_dong.api;

import org.springframework.http.MediaType;
import club.gach_dong.annotation.RequestUserReferenceId;
import club.gach_dong.dto.request.UserProfileRequest;
import club.gach_dong.dto.response.UserProfileResponse;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 프로필 이미지 API", description = "사용자 프로필 이미지 관련 API")
@RestController
@RequestMapping("/api/v1")
public interface UserApiSpecification {

    @Operation(summary = "프로필 이미지 업로드", description = "사용자의 프로필 이미지를 업로드합니다.")
    @PostMapping(value = "/upload-profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<UserProfileResponse> uploadProfileImage(
            @Parameter(description = "업로드할 이미지 파일", required = true)
            @ModelAttribute UserProfileRequest userProfileRequest,
            @Parameter(hidden = true) @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "프로필 이미지 수정", description = "사용자의 프로필 이미지를 수정합니다.")
    @PostMapping(value = "/update-profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<UserProfileResponse> updateProfileImage(
            @Parameter(description = "수정할 이미지 파일") @ModelAttribute UserProfileRequest userProfileRequest,
            @Parameter(hidden = true) @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "프로필 이미지 삭제", description = "사용자의 프로필 이미지를 삭제합니다.")
    @DeleteMapping("/delete-profile-image")
    ResponseEntity<String> deleteProfileImage(
            @Parameter(hidden = true) @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "프로필 이미지 조회", description = "사용자의 프로필 이미지를 조회합니다.")
    @GetMapping("/profile-image/{userReferenceId}")
    ResponseEntity<String> getProfileImage(
            @Parameter(description = "사용자 참조 ID", required = true)
            @PathVariable String userReferenceId);
}
