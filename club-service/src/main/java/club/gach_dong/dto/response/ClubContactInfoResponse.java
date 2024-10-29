package club.gach_dong.dto.response;

import club.gach_dong.domain.ContactInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public record ClubContactInfoResponse(
        @Schema(description = "연락 수단 (예: gmail, phone)", example = "gmail")
        String contactMethod,

        @Schema(description = "연락처 정보", example = "gachdong@gmail.com")
        String contactValue
) {
    public static ClubContactInfoResponse from(ContactInfo contactInfo) {
        return new ClubContactInfoResponse(
                contactInfo.getContactMethod(),
                contactInfo.getContactValue()
        );
    }
}
