package club.gach_dong.dto.response;

import club.gach_dong.domain.Club;
import club.gach_dong.domain.ClubAdmin;
import club.gach_dong.domain.ClubAdminRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AdminAuthorizedClubResponse(
        @Schema(description = "동아리 ID", example = "1", nullable = false)
        @NotNull
        Long clubId,

        @Schema(description = "동아리 이름", example = "가츠동", nullable = false)
        @NotNull
        String clubName,

        @Schema(description = "동아리 관리자 권한", example = "CLUB_ADMIN", nullable = false)
        @NotNull
        ClubAdminRole clubAdminRole,

        @Schema(description = "동아리 이미지 URL", example = "https://example.com/club.png")
        @NotNull
        String clubImageUrl
) {
    public static AdminAuthorizedClubResponse from(Club club, ClubAdmin admin) {
        return new AdminAuthorizedClubResponse(
                club.getId(),
                club.getName(),
                admin.getClubAdminRole(),
                club.getClubImageUrl()
        );
    }
}