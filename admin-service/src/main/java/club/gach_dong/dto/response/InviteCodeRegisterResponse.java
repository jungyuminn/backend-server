package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import club.gach_dong.entity.InviteCode;

public record InviteCodeRegisterResponse(
        @Schema(description = "발행한 유저 참조 ID", example = "user123")
        String createUserReferenceId,

        @Schema(description = "등록한 유저 참조 ID", example = "user456")
        String registerUserReferenceId,

        @Schema(description = "초대 코드", example = "abc1234-xyz")
        String inviteCode,

        @Schema(description = "만료 날짜", example = "2024-11-20T10:15:30")
        String expiryDate,

        @Schema(description = "클럽 ID", example = "123")
        Long clubId
) {
    public static InviteCodeRegisterResponse from(InviteCode inviteCode, String registeredBy) {
        return new InviteCodeRegisterResponse(
                inviteCode.getUserReferenceId(),
                registeredBy,
                inviteCode.getInviteCode(),
                inviteCode.getExpiryDate().toString(),
                inviteCode.getClubId()
        );
    }
}