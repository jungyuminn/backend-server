package club.gach_dong.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record InviteCodeRequest(
        @NotNull
        @Schema(description = "동아리 이름", example = "MyClub")
        String clubName,

        @NotNull
        @Schema(description = "초대코드", example = "abc123")
        String inviteCode
) {}
