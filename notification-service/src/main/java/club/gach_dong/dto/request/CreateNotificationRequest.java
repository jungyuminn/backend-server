package club.gach_dong.dto.request;

public record CreateNotificationRequest(
        String type,
        String publishType
) {
}
