package club.gach_dong.dto.response;

import club.gach_dong.domain.ContactInfo;
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
    public static CreateClubContactInfoResponse of(ContactInfo contactInfo) {
        return new CreateClubContactInfoResponse(
                contactInfo.getClub().getId(),
                contactInfo.getId()
        );
    }
}
