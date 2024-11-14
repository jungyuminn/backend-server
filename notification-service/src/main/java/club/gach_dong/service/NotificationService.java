package club.gach_dong.service;

import static club.gach_dong.exception.NotificationException.*;

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

    public List<NotificationResponse> getUserNotifications() {
        UserProfileResponse userProfile = getUserProfileResponse();

        List<Notification> notifications = notificationRepository.findByUserEmailAndPublishType(userProfile.email(), "EMAIL");
        return notifications.stream()
                .map(NotificationResponse::from)
                .toList();
    }

    public NotificationResponse createAndPublishNotification(CreateNotificationRequest request) {
        Notification notification = Notification.from(request.userEmail(), request.clubId(), request.title(), request.content(), request.publishType());
        notificationRepository.save(notification);

        switch (request.publishType()) {
            case "EMAIL":
                emailService.sendEmailNotice(notification.getUserEmail(), notification.getTitle(), notification.getMessage());
                break;
            case "WEB":
                // TODO : 추후에 Web Push Notification을 전송하도록 수정
                break;
            default:
                throw new NotSupportedPublishTypeException();
        }
        // TODO : 추후에 RabbitMQ를 사용하여 Notification을 전송하도록 수정
//        rabbitTemplate.convertAndSend("notifications", notification);
        return NotificationResponse.from(notification);
    }

    private UserProfileResponse getUserProfileResponse() {
        return restClient.get()
                .uri(gatewayEndpoint + "/auth/api/v1/profile")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatusCode -> !httpStatusCode.is2xxSuccessful(), (request, response) -> new AuthAPIFailedException())
                .body(UserProfileResponse.class);
    }
}

