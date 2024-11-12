package club.gach_dong.repository;

import club.gach_dong.domain.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
    NotificationTemplate findByType(String type);
}
