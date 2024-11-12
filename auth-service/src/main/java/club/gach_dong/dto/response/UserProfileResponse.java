package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import club.gach_dong.entity.Admin;
import club.gach_dong.entity.User;

public record UserProfileResponse(
        @Schema(description = "사용자 이메일", example = "user@gachon.ac.kr")
        String email,

        @Schema(description = "사용자 이름", example = "홍길동")
        String name,

        @Schema(description = "사용자 권한", example = "USER, ADMIN")
        String role,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.png")
        String profileImageUrl
) {
    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
                user.getEmail(),
                user.getName(),
                user.getRole().name(),
                user.getProfileImageUrl()
        );
    }

    public static UserProfileResponse from(Admin admin) {
        return new UserProfileResponse(
                admin.getEmail(),
                admin.getName(),
                admin.getRole().name(),
                admin.getProfileImageUrl()
        );
    }
}
