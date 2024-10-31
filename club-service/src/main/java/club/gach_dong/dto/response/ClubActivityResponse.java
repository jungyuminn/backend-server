package club.gach_dong.dto.response;

import club.gach_dong.domain.Activity;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ClubActivityResponse (
        @Schema(description = "활동 제목", example = "2024년 봄 캠프")
        @NotNull
        String title,

        @Schema(description = "활동 날짜", example = "2024-04-12")
        @NotNull
        LocalDate date,

        @Schema(description = "활동 설명", example = "봄 캠프에서 다양한 활동을 했습니다.")
        @NotNull
        String description
) {
    public static ClubActivityResponse from(Activity activity) {
        return new ClubActivityResponse(
                activity.getTitle(),
                activity.getDate(),
                activity.getDescription()
        );
    }
}
