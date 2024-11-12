package club.gach_dong.dto.response;

import club.gach_dong.domain.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record NotificationResponse(
        @Schema(description = "알림 ID", example = "1", nullable = false)
        Long id,
        @Schema(description = "알림 메시지", example = "새로운 알림이 도착했습니다", nullable = false)
        String message,
        @Schema(description = "알림 읽음 여부", example = "false", nullable = false)
        boolean isRead,
        @Schema(description = "알림 생성 시간", example = "2021-08-01T00:00:00", nullable = false)
        LocalDateTime createdAt
) {
    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}
