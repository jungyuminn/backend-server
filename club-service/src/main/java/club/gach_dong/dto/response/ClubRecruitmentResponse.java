package club.gach_dong.dto.response;

import club.gach_dong.domain.Club;
import club.gach_dong.domain.ClubCategory;
import club.gach_dong.domain.Recruitment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ClubRecruitmentResponse(
        @Schema(description = "동아리 id", example = "1")
        @NotNull
        Long clubId,

        @Schema(description = "모집공고 이름", example = "GDSC Gachon 24-25 Member 모집")
        @NotNull
        String title,

        @Schema(description = "동아리 이름", example = "가츠동")
        @NotNull
        String clubName,

        @Schema(description = "모집 시작일", example = "2021-09-01")
        @NotNull
        LocalDateTime startDate,

        @Schema(description = "모집 마감일", example = "2021-09-30")
        @NotNull
        LocalDateTime endDate,

        @Schema(description = "동아리 카테고리", example = "SPORTS")
        @NotNull
        ClubCategory category

) {
    public static ClubRecruitmentResponse of(Club club, Recruitment recruitment) {
        return new ClubRecruitmentResponse(
                club.getId(),
                recruitment.getTitle(),
                club.getName(),
                recruitment.getStartDate(),
                recruitment.getEndDate(),
                club.getCategory()
        );
    }
}
