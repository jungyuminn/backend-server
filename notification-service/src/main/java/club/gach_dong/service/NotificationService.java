package club.gach_dong.service;

import club.gach_dong.dto.request.CreateNotificationRequest;
import club.gach_dong.dto.response.NotificationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    public List<NotificationResponse> getUserNotifications(String userReferenceId) {
        return null;
    }

    public NotificationResponse createAndPublishNotification(CreateNotificationRequest request) {
        return null;
    }
}
