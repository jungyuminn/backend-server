package club.gach_dong.dto.request;

import club.gach_dong.domain.Club;
import club.gach_dong.domain.ContactInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UpdateContactInfoRequest(
        @Schema(description = "동아리 ID", example = "1")
        @NotNull
        Long clubId,

        @Schema(description = "연락처 ID", example = "1")
        @NotNull
        Long contactInfoId,

        @Schema(description = "연락처 유형", example = "전화번호")
        @NotNull
        String type,

        @Schema(description = "연락처", example = "010-1234-5678")
        @NotNull
        String contact
) implements ClubIdentifiable {
    public void updateToEntity(ContactInfo contactInfo) {
        contactInfo.update(type, contact);
    }
}
