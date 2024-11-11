package club.gach_dong.controller;

import club.gach_dong.api.NotificationApiSpecification;
import club.gach_dong.dto.request.CreateNotificationRequest;
import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.NotificationResponse;
import club.gach_dong.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NotificationController implements NotificationApiSpecification {

    private final NotificationService notificationService;

    @Override
    public ArrayResponse<NotificationResponse> getUserNotifications(String userReferenceId, HttpServletRequest request) {
        List<NotificationResponse> notifications = notificationService.getUserNotifications(userReferenceId);
        return ArrayResponse.of(notifications);
    }

    @Override
    public ResponseEntity<NotificationResponse> createNotification(String userReferenceId, CreateNotificationRequest request) {
        NotificationResponse response = notificationService.createAndPublishNotification(userReferenceId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
