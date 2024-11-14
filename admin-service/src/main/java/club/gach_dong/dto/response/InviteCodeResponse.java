package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import club.gach_dong.entity.InviteCode;

public record InviteCodeResponse(
        @Schema(description = "유저 참조 ID", example = "user123")
        String userReferenceId,

        @Schema(description = "초대 코드", example = "abc1234-xyz")
        String inviteCode,

        @Schema(description = "만료 날짜", example = "2024-11-20T10:15:30")
        String expiryDate,

        @Schema(description = "클럽 ID", example = "123")
        Long clubId
) {
    public static InviteCodeResponse from(InviteCode inviteCode) {
        return new InviteCodeResponse(
                inviteCode.getUserReferenceId(),
                inviteCode.getInviteCode(),
                inviteCode.getExpiryDate().toString(),
                inviteCode.getClubId()
        );
    }
}