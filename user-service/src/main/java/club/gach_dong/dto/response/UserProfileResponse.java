package club.gach_dong.dto.response;

import club.gach_dong.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UserProfileResponse(
        @Schema(description = "사용자 ID", example = "1")
        @NotNull
        Long id,

        @Schema(description = "사용자 참조 ID", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull
        String userReferenceId,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/image.png")
        @NotNull
        String profileImageUrl
) {
    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getUserReferenceId(),
                user.getProfileImageUrl()
        );
    }
}
