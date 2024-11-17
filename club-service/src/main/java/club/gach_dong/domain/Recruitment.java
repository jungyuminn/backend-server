package club.gach_dong.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Entity
@Table(name = "recruitment")
@Schema(description = "동아리 모집 정보를 나타내는 엔티티")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "모집 ID", example = "1")
    private Long id;

    @Column(name = "title", nullable = false)
    @Schema(description = "모집 제목", example = "GDSC Gachon 2024 멤버 모집")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    @Schema(description = "공고 내용", example = "<h1>GDSC Gachon 2024 멤버를 모집합니다.</h1> ..")
    private String content;

    @Column(name = "application_form_id", nullable = false)
    @Schema(description = "모집 공고와 연결된 지원서 양식 ID", example = "1")
    private Long applicationFormId;

    @Column(name = "recruitment_status", nullable = false)
    @Schema(description = "모집 상태", example = "true")
    private boolean recruitmentStatus = true;

    @Column(name = "recruitment_count", nullable = false)
    @Schema(description = "모집 인원", example = "10")
    private Long recruitmentCount;

    @Column(name = "start_date", nullable = false)
    @Schema(description = "모집 시작일", example = "2024-03-01")
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @Schema(description = "모집 종료일", example = "2024-03-15")
    private LocalDateTime endDate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "process_data", nullable = false, columnDefinition = "json", length = 30000)
    @Schema(name = "모집 프로세스 설정", example = "{\n" +
            "  \"process1\": \"서류 심사\",\n" +
            "  \"process2\": \"1차 면접\",\n" +
            "  \"process3\": \"2차 면접\"\n" +
            " \"process4\": \"최종 합격\"\n" +
            "}")
    private Map<String, Object> processData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Builder
    private Recruitment(String title, String content, Long recruitmentCount, Long applicationFormId, LocalDateTime startDate, LocalDateTime endDate, Map<String, Object> processData, Club club) {
        this.title = title;
        this.content = content;
        this.recruitmentCount = recruitmentCount;
        this.applicationFormId = applicationFormId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.processData = processData;
        this.club = club;
    }

    public Boolean isRecruiting(LocalDateTime currentDateTime) {
        return currentDateTime.isBefore(this.endDate);
    }
}
