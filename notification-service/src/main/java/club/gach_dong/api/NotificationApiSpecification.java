package club.gach_dong.api;

import club.gach_dong.annotation.RequestUserReferenceId;
import club.gach_dong.dto.request.CreateNotificationRequest;
import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Validated
public interface NotificationApiSpecification {

    @Operation(
            summary = "사용자 알림 조회",
            description = "사용자의 알림을 조회합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    @GetMapping()
    public ArrayResponse<NotificationResponse> getUserNotifications(
            @RequestUserReferenceId
            String userReferenceId
    );

    @Operation(
            summary = "새로운 알림 생성",
            description = "새로운 알림을 생성합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    @PostMapping()
    public ResponseEntity<NotificationResponse> createNotification(
            @RequestBody CreateNotificationRequest request
    );

}
