package club.gach_dong.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record TransferRequest(
        @NotNull
        @Schema(description = "동아리 이름", example = "MyClub")
        String clubName,

        @NotNull
        @Schema(description = "대상 사용자 ID", example = "user456")
        String targetUserReferenceId
) {}
