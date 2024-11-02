package club.gach_dong.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import club.gach_dong.dto.request.UserProfileRequest;
import club.gach_dong.dto.response.UserProfileResponse;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 프로필 이미지 API", description = "사용자 프로필 이미지 관련 API")
public interface UserApiSpecification {

    @Operation(summary = "프로필 이미지 업로드", description = "사용자의 프로필 이미지를 업로드합니다.")
    @PostMapping(value = "/upload_profile_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<UserProfileResponse> uploadProfileImage(
            @Parameter(description = "업로드할 이미지 파일", required = true)
            @ModelAttribute UserProfileRequest userProfileRequest,
            HttpServletRequest httpServletRequest);

    @Operation(summary = "프로필 이미지 수정", description = "사용자의 프로필 이미지를 수정합니다.")
    @PostMapping(value = "/update_profile_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<UserProfileResponse> updateProfileImage(
            @Parameter(description = "수정할 이미지 파일") @ModelAttribute UserProfileRequest userProfileRequest,
            HttpServletRequest httpServletRequest);

    @Operation(summary = "프로필 이미지 삭제", description = "사용자의 프로필 이미지를 삭제합니다.")
    @DeleteMapping("/delete_profile_image")
    ResponseEntity<String> deleteProfileImage(
            @Parameter(description = "HTTP 요청 (X-MEMBER-ID 헤더 포함)") HttpServletRequest httpServletRequest);

    @Operation(summary = "프로필 이미지 조회", description = "사용자의 프로필 이미지를 조회합니다.")
    @GetMapping("/profile_image")
    ResponseEntity<String> getProfileImage(
            @Parameter(description = "HTTP 요청 (X-MEMBER-ID 헤더 포함)") HttpServletRequest httpServletRequest);
}