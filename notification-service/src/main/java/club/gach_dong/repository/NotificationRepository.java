package club.gach_dong.repository;

import club.gach_dong.domain.Notification;
import java.lang.annotation.Native;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserReferenceId(String userReferenceId);

    List<Notification> findByUserReferenceIdAndPublishType(String userReferenceId, String publishType);
}
