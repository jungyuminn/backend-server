package club.gach_dong.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "start_date", nullable = false)
    @Schema(description = "모집 시작일", example = "2024-03-01")
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @Schema(description = "모집 종료일", example = "2024-03-15")
    private LocalDate endDate;


    private Recruitment(String title, LocalDate startDate, LocalDate endDate, Club club) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.club = club;
    }

    public static Recruitment of(String title, LocalDate startDate, LocalDate endDate, Club club) {
        return new Recruitment(title, startDate, endDate, club);
    }
}
