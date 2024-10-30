package club.gach_dong.api;

import org.springframework.web.multipart.MultipartFile;
import club.gach_dong.dto.UserProfileDto;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "프로필 이미지 API", description = "사용자 프로필 이미지 관련 API")
public interface UserApiSpecification {

    @Operation(summary = "프로필 이미지 업로드", description = "사용자의 프로필 이미지를 업로드합니다.")
    @PostMapping("/{userId}/upload_profile_image")
    ResponseEntity<UserProfileDto> uploadProfileImage(
            @Parameter(description = "업로드할 이미지 파일") @RequestParam("image") MultipartFile image,
            @Parameter(description = "사용자 ID") @PathVariable("userId") String userId);

    @Operation(summary = "프로필 이미지 수정", description = "사용자의 프로필 이미지를 수정합니다.")
    @PostMapping("/{userId}/update_profile_image")
    ResponseEntity<UserProfileDto> updateProfileImage(
            @Parameter(description = "수정할 이미지 파일") @RequestParam("image") MultipartFile image,
            @Parameter(description = "사용자 ID") @PathVariable("userId") String userId);

    @Operation(summary = "프로필 이미지 삭제", description = "사용자의 프로필 이미지를 삭제합니다.")
    @DeleteMapping("/{userId}/delete_profile_image")
    ResponseEntity<String> deleteProfileImage(
            @Parameter(description = "사용자 ID") @PathVariable("userId") String userId);
}
