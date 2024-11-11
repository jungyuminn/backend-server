package club.gach_dong.service;

import club.gach_dong.domain.Notification;
import club.gach_dong.domain.NotificationTemplate;
import club.gach_dong.dto.request.CreateNotificationRequest;
import club.gach_dong.dto.response.NotificationResponse;
import club.gach_dong.dto.response.UserProfileResponse;
import club.gach_dong.repository.NotificationRepository;
import club.gach_dong.repository.NotificationTemplateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class NotificationService {

    // TODO : 추후에 RabbitMQ를 사용하여 Notification을 전송하도록 수정
//    private final RabbitTemplate rabbitTemplate;

    @Value("${app.gateway.endpoint}")
    private String gatewayEndpoint;

    private final EmailService emailService;
    private final RestClient restClient;
    private final NotificationRepository notificationRepository;
    private final NotificationTemplateRepository templateRepository;

    public List<NotificationResponse> getUserNotifications(String userReferenceId) {
        List<Notification> notifications = notificationRepository.findByUserReferenceIdAndPublishType(userReferenceId, "EMAIL");
        return notifications.stream()
                .map(NotificationResponse::from)
                .toList();
    }

    public NotificationResponse createAndPublishNotification(String userReferenceId, CreateNotificationRequest request) {
        UserProfileResponse userProfile = restClient.get()
                .uri(gatewayEndpoint+"/auth/api/v1/profile")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(UserProfileResponse.class);

        String userEamil = userProfile.email();

        NotificationTemplate template = templateRepository.findByType(request.type());
        Notification notification = Notification.from(userReferenceId, request.publishType(), template);
        notificationRepository.save(notification);

        switch (request.publishType()) {
            case "EMAIL":
                emailService.sendEmailNotice(userEamil, notification.getTitle(), notification.getMessage());
                break;
            default:
                break;
        }
        // TODO : 추후에 RabbitMQ를 사용하여 Notification을 전송하도록 수정
//        rabbitTemplate.convertAndSend("notifications", notification);
        return NotificationResponse.from(notification);
    }
}

