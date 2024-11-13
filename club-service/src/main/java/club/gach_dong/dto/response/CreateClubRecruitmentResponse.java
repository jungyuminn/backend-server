package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateClubRecruitmentResponse(

        @Schema(description = "동아리 ID", example = "1")
        @NotNull
        Long clubId,

        @Schema(description = "동아리 모집 ID", example = "1", nullable = false)
        @NotNull
        Long clubRecruitmentId
) {
    public static CreateClubRecruitmentResponse from(Long clubId, Long clubRecruitmentId) {
        return new CreateClubRecruitmentResponse(clubId, clubRecruitmentId);
    }
}
