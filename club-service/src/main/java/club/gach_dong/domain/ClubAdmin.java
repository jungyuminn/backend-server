package club.gach_dong.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "club_admin")
@Schema(description = "동아리 관리자 엔티티")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubAdmin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "동아리 관리자 ID", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    @Schema(description = "동아리")
    private Club club;

    @Column(name = "user_reference_id", columnDefinition = "CHAR(36)")
    @Schema(description = "사용자 참조 ID", example = "00000000-0000-0000-0000-000000000000")
    private String userReferenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "club_admin_role", nullable = false)
    @Schema(description = "동아리 관리자 권한", example = "PRESIDENT")
    private ClubAdminRole clubAdminRole;

    @Column(name = "authorized_at", columnDefinition = "TIMESTAMP")
    @Schema(description = "권한 부여 일시", example = "2021-09-01T10:15:30")
    private LocalDateTime authorizedAt;

    public static ClubAdmin createPresident(Club club, String userReferenceId) {
        ClubAdmin admin = new ClubAdmin();
        admin.club = club;
        admin.userReferenceId = userReferenceId;
        admin.clubAdminRole = ClubAdminRole.PRESIDENT;
        admin.authorizedAt = LocalDateTime.now();
        return admin;
    }

    public static ClubAdmin createMember(String userReferenceId, Club club) {
        ClubAdmin admin = new ClubAdmin();
        admin.club = club;
        admin.userReferenceId = userReferenceId;
        admin.clubAdminRole = ClubAdminRole.MEMBER;
        admin.authorizedAt = LocalDateTime.now();
        return admin;

    }
}