package club.gach_dong.dto.request;

import club.gach_dong.domain.Club;
import club.gach_dong.domain.Recruitment;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Map;

public record CreateClubRecruitmentRequest(
        @Schema(description = "동아리 ID", example = "1", nullable = false)
        @NotNull
        Long clubId,
        @Schema(description = "모집 제목", example = "2021년도 1학기 신입부원 모집", nullable = false)
        @NotNull
        String title,
        @Schema(description = "공고 내용", example = "신입부원을 모집합니다.", nullable = false)
        @NotNull
        String content,
        @Schema(description = "모집 인원", example = "10", nullable = false)
        @NotNull
        Long recruitmentCount,
        @Schema(description = "모집 공고와 연결된 지원서 양식 ID", example = "1", nullable = false)
        @NotNull
        Long applicationFormId,
        @Schema(description = "모집 시작일", example = "2021-01-01", nullable = false)
        @NotNull
        LocalDateTime startDate,
        @Schema(description = "모집 마감일", example = "2021-01-31", nullable = false)
        @NotNull
        LocalDateTime endDate,
        @Schema(
                description = "모집 프로세스 설정, Json 형식으로 넣어주세요.",
                nullable = false,
                example = "{\n" +
                        "  \"process1\": \"서류 심사\",\n" +
                        "  \"process2\": \"1차 면접\",\n" +
                        "  \"process3\": \"2차 면접\"\n" +
                        " \"process4\": \"최종 합격\"\n" +
                        "}"
        )
        @NotNull(message = "모집 프로세스 설정이 누락되었습니다.")
        @Size(max = 2000, message = "모집 프로세스 설정 너무 큽니다.")
        @JsonProperty("processData")
        Map<String, Object> processData
) {

    public Recruitment toEntity(Club club) {
        return Recruitment.builder()
                .club(club)
                .title(title())
                .content(content())
                .applicationFormId(applicationFormId())
                .recruitmentCount(recruitmentCount())
                .startDate(startDate())
                .endDate(endDate())
                .processData(processData())
                .build();
    }
}
