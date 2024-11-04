package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import test.user.entity.User;

public record UserProfileResponse(
        @Schema(description = "사용자 ID", example = "1")
        @NotNull
        Long id,

        @Schema(description = "사용자 이메일", example = "user@gachon.ac.kr")
        @NotNull
        String email,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/image.png")
        @NotNull
        String profileImageUrl
) {
    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getProfileImageUrl()
        );
    }
}
