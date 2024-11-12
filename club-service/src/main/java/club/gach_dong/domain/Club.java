package club.gach_dong.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "club")
@Schema(description = "동아리 엔티티")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT")
    @Schema(description = "동아리 ID", example = "1")
    private Long id;

    @Column(name = "club_name", length = 26, columnDefinition = "CHAR(26)")
    @Schema(description = "동아리 이름", example = "GDG Gachon")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "club_category", nullable = false)
    @Schema(description = "동아리 카테고리", example = "IT")
    private ClubCategory category;

    @Column(name = "club_short_description", nullable = false)
    @Schema(description = "동아리 한줄 설명", example = "Google 기술을 연구하는 동아리")
    private String shortDescription;

    @Column(name = "club_introduction", columnDefinition = "TEXT")
    @Schema(description = "동아리 소개 (HTML 또는 Markdown)", example = "<h1>GDG Gachon</h1><p>Google 기술을 연구하는 동아리...</p>")
    private String introduction;

    @Column(name = "club_image_url")
    @Schema(description = "동아리 이미지 URL", example = "https://example.com/image.png")
    private String clubImageUrl = "https://www.pngarts.com/files/10/Default-Profile-Picture-PNG-Download-Image.png";

    @Column(name = "recruiting_status")
    @Schema(description = "모집 상태", example = "true")
    private boolean recruitingStatus = false;

    @Column(name = "club_established_at")
    @Schema(description = "동아리 설립 날짜", example = "2020-03-15T10:15:30")
    private LocalDateTime establishedAt;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "동아리 활동 목록")
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "동아리 모집 정보")
    private List<Recruitment> recruitment = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "동아리 연락처 정보")
    private List<ContactInfo> contactInfo = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "동아리 관리자 목록")
    private List<ClubAdmin> admins = new ArrayList<>();

    private Club(
            String name,
                 ClubCategory category,
                 String shortDescription,
                 String introduction,
                 String clubImageUrl,
                 String userReferenceId,
                 LocalDateTime establishedAt
    ) {
        this.name = name;
        this.category = category;
        this.shortDescription = shortDescription;
        this.introduction = introduction;
        this.clubImageUrl = clubImageUrl;
        this.establishedAt = establishedAt;
        addPresident(userReferenceId);
    }

    public static Club of(
            String name,
            ClubCategory category,
            String shortDescription,
            String introduction,
            String clubImageUrl,
            String userReferenceId,
            LocalDateTime establishedAt
    ) {
        return new Club(name, category, shortDescription, introduction, clubImageUrl, userReferenceId, establishedAt);
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void addRecruitment(Recruitment recruitment) {
        this.recruitment.add(recruitment);
    }

    public void addContactInfo(ContactInfo contactInfo) {
        this.contactInfo.add(contactInfo);
    }

    public void addPresident(String userReferenceId) {
        ClubAdmin presidentAdmin = ClubAdmin.createPresident(this, userReferenceId);
        this.admins.add(presidentAdmin);
    }

    public void addAdminMember(ClubAdmin clubAdmin) {
        this.admins.add(clubAdmin);
    }
}