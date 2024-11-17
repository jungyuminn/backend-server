package club.gach_dong.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name = "applicationForm")
public class ApplicationForm {
    @Id
    @Column(name = "application_form_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_form_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ApplicationFormStatus applicationFormStatus;

    @Column(name = "form_name", nullable = false, length = 50)
    private String formName;

    @Column(name = "club_Id", nullable = false)
    private Long clubId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "application_form_body", nullable = false, columnDefinition = "json", length = 2000)
    private Map<String, Object> body;
}