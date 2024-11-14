package club.gach_dong.repository;

import club.gach_dong.domain.Notification;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.Native;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserEmailAndPublishType(@NotNull String userEmail, @NotNull String publishType);
}
