package club.gach_dong.dto.response;

import club.gach_dong.domain.Club;
import club.gach_dong.domain.Recruitment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ClubRecruitmentDetailResponse(
        @Schema(description = "동아리 ID", example = "1")
        @NotNull
        Long clubId,
        @Schema(description = "모집 ID", example = "1")
        @NotNull
        Long recruitmentId,
        @Schema(description = "모집공고 이름", example = "GDSC Gachon 24-25 Member 모집")
        @NotNull
        String title,
        @Schema(description = "모집공고 내용", example = "GDSC Gachon 24-25 Member 모집합니다.")
        @NotNull
        String content,
        @Schema(description = "모집 인원", example = "5")
        @NotNull
        Long recruitmentCount,
        @Schema(description = "모집 상태", example = "true")
        @NotNull
        boolean recruitmentStatus,
        @Schema(description = "모집 시작일", example = "2021-09-01")
        @NotNull
        LocalDateTime startDate,
        @Schema(description = "모집 마감일", example = "2021-09-30")
        @NotNull
        LocalDateTime endDate
) {
    public static ClubRecruitmentDetailResponse of(Club club, Recruitment recruitment) {
        return new ClubRecruitmentDetailResponse(
                club.getId(),
                recruitment.getId(),
                recruitment.getTitle(),
                recruitment.getContent(),
                recruitment.getRecruitmentCount(),
                recruitment.isRecruitmentStatus(),
                recruitment.getStartDate(),
                recruitment.getEndDate()
        );
    }
}
