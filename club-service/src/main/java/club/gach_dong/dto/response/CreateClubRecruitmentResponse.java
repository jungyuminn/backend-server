package club.gach_dong.dto.response;

import club.gach_dong.domain.Recruitment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateClubRecruitmentResponse(

        @Schema(description = "동아리 ID", example = "1")
        @NotNull
        Long clubId,

        @Schema(description = "동아리 모집 ID", example = "1", nullable = false)
        @NotNull
        Long clubRecruitmentId,

        @Schema(description = "동아리 지원서 양식 ID", example = "1", nullable = false)
        @NotNull
        Long applicationFormId

) {
    public static CreateClubRecruitmentResponse of(Recruitment recruitment) {
        return new CreateClubRecruitmentResponse(
                recruitment.getClub().getId(),
                recruitment.getId(),
                recruitment.getApplicationFormId()
        );
    }
}
