package club.gach_dong.dto.response;

import club.gach_dong.domain.ClubAdmin;
import club.gach_dong.domain.ClubAdminRole;
import io.swagger.v3.oas.annotations.media.Schema;

public record AdminInfoResponse(
        @Schema(description = "club Id", example = "1")
        Long clubId,

        @Schema(description = "user reference Id", example = "00000000-0000-0000-0000-000000000000")
        String userReferenceId,

        @Schema(description = "club admin role", example = "PRESIDENT")
        ClubAdminRole clubAdminRole


) {
    public static AdminInfoResponse from(ClubAdmin admin) {
        return new AdminInfoResponse(
                admin.getClub().getId(),
                admin.getUserReferenceId(),
                admin.getClubAdminRole()
        );
    }
}
