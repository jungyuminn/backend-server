package club.gach_dong.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateNotificationRequest(
        @Schema(description = "동아리 ID", example = "1", nullable = false)
        Long clubId,
        @Schema(description = "사용자 email", example = "leecm6183@gachon.ac.kr", nullable = false)
        String userEmail,
        @Schema(description = "알림 제목", example = "알림 제목", nullable = false)
        String title,
        @Schema(description = "알림 내용", example = "알림 내용", nullable = false)
        String content,
        @Schema(description = "알림 발행 유형", example = "EMAIL", nullable = false)
        String publishType
) {
}
