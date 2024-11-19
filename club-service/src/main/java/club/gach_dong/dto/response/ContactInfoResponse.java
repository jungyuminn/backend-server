package club.gach_dong.dto.response;

import club.gach_dong.domain.ContactInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ContactInfoResponse(
        @Schema(description = "연락처 ID", example = "1")
        @NotNull
        Long id,

        @Schema(description = "연락 수단 (예: gmail, phone)", example = "gmail")
        @NotNull
        String contactMethod,

        @Schema(description = "연락처 정보", example = "gachdong@gmail.com")
        @NotNull
        String contactValue
) {
    public static ContactInfoResponse from(ContactInfo contactInfo) {
        return new ContactInfoResponse(
                contactInfo.getId(),
                contactInfo.getContactMethod(),
                contactInfo.getContactValue()
        );
    }
}
