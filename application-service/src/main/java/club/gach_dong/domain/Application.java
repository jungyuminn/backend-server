package club.gach_dong.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private Long userId;

    @Column(name = "apply_id", nullable = false)
    private Long applyId;

    @Column(name = "application_form_id", nullable = false)
    private Long applicationFormId;

    @Column(name = "application_body", nullable = false, length = 30000)
    private String applicationBody;

    @Column(name = "application_status", nullable = false, length = 30)
    private String applicationStatus;

    @Column(name = "club_name", nullable = false, length = 80)
    private String clubName;

}
