package club.gach_dong.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "recruitment")
@Schema(description = "동아리 모집 정보를 나타내는 엔티티")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "모집 ID", example = "1")
    private Long id;

    @Column(name = "title", nullable = false)
    @Schema(description = "모집 제목", example = "GDSC Gachon 2024 멤버 모집")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    @Schema(description = "공고 내용", example = "<h1>GDSC Gachon 2024 멤버를 모집합니다.</h1> ..")
    private String content;

    @Column(name = "recruitment_status", nullable = false)
    @Schema(description = "모집 상태", example = "true")
    private boolean recruitmentStatus;

    @Column(name = "recruitment_count", nullable = false)
    @Schema(description = "모집 인원", example = "10")
    private Long recruitmentCount;

    @Column(name = "start_date", nullable = false)
    @Schema(description = "모집 시작일", example = "2024-03-01")
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @Schema(description = "모집 종료일", example = "2024-03-15")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private Recruitment(String title, String content, Long recruitmentCount, Boolean recruitmentStatus, LocalDateTime startDate, LocalDateTime endDate, Club club) {
        this.title = title;
        this.content = content;
        this.recruitmentCount = recruitmentCount;
        this.recruitmentStatus = recruitmentStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.club = club;
    }

    public static Recruitment of(String title, String content, Long recruitmentCount, Boolean recruitmentStatus, LocalDateTime startDate, LocalDateTime endDate, Club club) {
        return new Recruitment(title, content, recruitmentCount, recruitmentStatus, startDate, endDate, club);
    }

    // TODO: 날짜 계산 로직 개선 + admin feature 에서 구현 예정
    public Boolean isRecruiting(LocalDateTime currentDate) {
        long remainingDay = ChronoUnit.DAYS.between(currentDate, endDate);
        return false;
    }
}
