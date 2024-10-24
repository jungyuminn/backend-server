package club.gach_dong.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "activity")
@Schema(description = "동아리 활동 정보를 나타내는 엔티티")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "활동 ID", example = "1")
    private Long id;

    @Column(name = "title", nullable = false)
    @Schema(description = "활동 제목", example = "2023년 여름 프로젝트")
    private String title;

    @Column(name = "date", nullable = false)
    @Schema(description = "활동 날짜", example = "2023-08-31")
    private LocalDate date;

    @Column(name = "description", nullable = false)
    @Schema(description = "활동 설명", example = "팀별 프로젝트 진행 및 발표")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private Activity(String title, LocalDate date, String description, Club club) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.club = club;
    }

    public static Activity of(String title, LocalDate date, String description, Club club) {
        return new Activity(title, date, description, club);
    }
}
