package club.gach_dong.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AuthorizeAdminRequest(
        @NotNull
        @Schema(description = "동아리 ID", example = "12345")
        Long clubId,

        @NotNull
        @Schema(description = "사용자 참조 ID", example = "userRef123")
        String userReferenceId
) {}
