package club.gach_dong.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
public class Club {

    @Id
    @Column(name = "club_id", length = 36, columnDefinition = "CHAR(36)")
    @Schema(description = "동아리 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

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
    private String clubImageUrl;

    @Column(name = "recruiting_status")
    @Schema(description = "모집 상태", example = "true")
    private boolean recruitingStatus;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "동아리 활동 목록")
    private List<Activity> activities;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "동아리 모집 정보")
    private List<Recruitment> recruitment;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "동아리 연락처 정보")
    private List<ContactInfo> contactInfo;

    @Column(name = "club_established_at")
    @Schema(description = "동아리 설립 날짜", example = "2020-03-15T10:15:30")
    private LocalDateTime establishedAt;

    @CreatedDate
    @Column(name = "created_at")
    @Schema(description = "생성 날짜", example = "2023-10-24T12:00:00")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_at")
    @Schema(description = "마지막 업데이트 날짜", example = "2023-10-24T12:00:00")
    private LocalDateTime updatedAt;

    private Club(String name, ClubCategory category, String shortDescription, String introduction,
                 String clubImageUrl, boolean recruitingStatus, List<Activity> activities, List<Recruitment> recruitment,
                 List<ContactInfo> contactInfo, LocalDateTime establishedAt) {
        this.name = name;
        this.category = category;
        this.shortDescription = shortDescription;
        this.introduction = introduction;
        this.clubImageUrl = clubImageUrl;
        this.recruitingStatus = recruitingStatus;
        this.activities = activities;
        this.recruitment = recruitment;
        this.contactInfo = contactInfo;
        this.establishedAt = establishedAt;
    }

    public static Club of(
            String name,
            ClubCategory category,
            String shortDescription,
            String introduction,
            String clubImageUrl,
            LocalDateTime establishedAt
    ) {
        return new Club(name, category, shortDescription, introduction, clubImageUrl, false,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), establishedAt);
    }
}
