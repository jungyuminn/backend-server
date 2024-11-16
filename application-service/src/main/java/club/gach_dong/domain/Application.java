package club.gach_dong.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "application")
public class Application {

    @Id
    @Column(name = "application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "recruitment_id", nullable = false)
    private Long recruitmentId;

    @Column(name = "application_form_id", nullable = false)
    private Long applicationFormId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "application_body", nullable = false, columnDefinition = "json", length = 30000)
    private Map<String, Object> applicationBody;

    @Column(name = "application_status", nullable = false, length = 30)
    private String applicationStatus;

    @Column(name = "club_name", nullable = false, length = 80)
    private String clubName;

    @Column(name = "submit_date", nullable = false)
    private LocalDateTime submitDate;

}
