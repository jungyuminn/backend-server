package club.gach_dong.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import club.gach_dong.dto.UserProfileDto;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 프로필 API", description = "사용자 프로필 이미지 관련 API")
public interface UserApiSpecification {

    @Operation(summary = "프로필 이미지 업로드", description = "사용자의 프로필 이미지를 업로드합니다. \n" + "- HTTP 요청 헤더에서 사용자 ID를 가져옵니다.")
    @PostMapping(value = "/upload_profile_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserProfileDto> uploadProfileImage(
            @Parameter(description = "업로드할 이미지 파일", required = true)
            @RequestParam("image") MultipartFile image,
            HttpServletRequest httpServletRequest);

    @Operation(summary = "프로필 이미지 수정", description = "사용자의 프로필 이미지를 수정합니다. \n" + "- HTTP 요청 헤더에서 사용자 ID를 가져옵니다.")
    @PostMapping(value = "/update_profile_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<UserProfileDto> updateProfileImage(
            @Parameter(description = "수정할 이미지 파일") @RequestParam("image") MultipartFile image, HttpServletRequest httpServletRequest);

    @Operation(summary = "프로필 이미지 삭제", description = "사용자의 프로필 이미지를 삭제합니다. \n" + "- HTTP 요청 헤더에서 사용자 ID를 가져옵니다.")
    @DeleteMapping("/delete_profile_image")
    ResponseEntity<String> deleteProfileImage(
            @Parameter(description = "HTTP 요청 (X-MEMBER-ID 헤더 포함)") HttpServletRequest httpServletRequest);
}
