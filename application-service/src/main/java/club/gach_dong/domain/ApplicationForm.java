package club.gach_dong.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
@Entity(name = "applicationForm")
public class ApplicationForm {
    @Id
    @Column(name = "application_form_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apply_id", nullable = false)
    private Long applyId;

    @Column(name = "application_form_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ApplicationFormStatus applicationFormStatus;

    @Column(name = "form_name", nullable = false, length = 50)
    private String formName;

    @Column(name = "application_form_body", nullable = false, length = 2000)
    private String body;
}