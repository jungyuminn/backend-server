package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateClubContactInfoResponse(
        @Schema(description = "동아리 ID", example = "1")
        @NotNull
        Long clubId,

        @Schema(description = "연락처 ID", example = "1")
        @NotNull
        Long ContactId
) {
    public static CreateClubContactInfoResponse from(Long clubId, Long contactId) {
        return new CreateClubContactInfoResponse(clubId, contactId);
    }
}
