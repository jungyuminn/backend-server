package club.gach_dong.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UserProfileRequest(
        @NotNull
        @Schema(description = "업로드할 프로필 이미지", example = "image.png")
        MultipartFile image
) {}
