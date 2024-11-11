package club.gach_dong.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "notifications")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userReferenceId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String publishType;

    private boolean isRead = false;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private Notification(
        String userReferenceId,
        String title,
        String message,
        String type,
        String publishType
    ) {
        this.userReferenceId = userReferenceId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.publishType = publishType;
    }

    public static Notification from(
      String userReferenceId,
      String publishType,
      NotificationTemplate template
    )  {
        return new Notification(userReferenceId, publishType, template.getTitle(), template.getContent(), template.getType());
    }
}
