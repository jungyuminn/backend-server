package club.gach_dong.dto.response;

import club.gach_dong.domain.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateClubActivityResponse(
        @Schema(description = "동아리 ID", example = "1")
        @NotNull
        Long clubId,

        @Schema(description = "활동 ID", example = "1")
        @NotNull
        Long activityId
) {
    public static CreateClubActivityResponse of(Activity activity) {
        return new CreateClubActivityResponse(
                activity.getClub().getId(),
                activity.getId()
        );
    }
}
