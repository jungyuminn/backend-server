package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import club.gach_dong.entity.User;

public record UserProfileResponse(
        @Schema(description = "사용자 ID", example = "1")
        Long id,

        @Schema(description = "사용자 이메일", example = "user@gachon.ac.kr")
        String email,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/image.png")
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
