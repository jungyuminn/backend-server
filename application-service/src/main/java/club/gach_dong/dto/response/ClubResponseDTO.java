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
        private Long clubId;
        private Long recruitmentId;
        private Integer viewCount;
        private String title;
        private String content;
        private Integer recruitmentCount;
        private Long applicationFormId;
        private String recruitmentStatus;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        @JsonProperty("processData")
        private Map<String, String> processData;

    }
}
