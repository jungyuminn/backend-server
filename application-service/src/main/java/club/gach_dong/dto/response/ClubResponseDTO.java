package club.gach_dong.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

public class ClubResponseDTO {
    @Getter
    @Builder
    @Schema(description = "내부 Service Mesh용 DTO (FrontEnd와는 관계가 없습니다.)")
    public static class RecruitmentResponseDto {
        @Schema(description = "동아리 ID", example = "1")
        Long clubId;
        @Schema(description = "모집 ID", example = "1")
        Long recruitmentId;
        @Schema(description = "모집공고 조회수", example = "1")
        int viewCount;
        @Schema(description = "모집공고 이름", example = "GDSC Gachon 24-25 Member 모집")
        String title;
        @Schema(description = "모집공고 내용", example = "GDSC Gachon 24-25 Member 모집합니다.")
        String content;
        @Schema(description = "모집 인원", example = "5")
        Long recruitmentCount;
        @Schema(description = "동아리 지원서 양식 ID", example = "1", nullable = false)
        Long applicationFormId;
        @Schema(description = "모집 상태", example = "true")
        String recruitmentStatus;
        @Schema(description = "모집 프로세스 설정", example = "{\n" +
                "  \"process1\": \"서류 심사\",\n" +
                "  \"process2\": \"1차 면접\",\n" +
                "  \"process3\": \"2차 면접\"\n" +
                " \"process4\": \"최종 합격\"\n" +
                "}")
        @JsonProperty("processData")
        Map<String, Object> processData;
        @Schema(description = "모집 시작일", example = "2021-09-01")
        LocalDateTime startDate;
        @Schema(description = "모집 마감일", example = "2021-09-30")
        LocalDateTime endDate;
    }
}
