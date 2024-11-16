package club.gach_dong.dto.request;

import club.gach_dong.domain.Activity;
import club.gach_dong.domain.Club;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

//@Getter
public record CreateClubActivityRequest(
        @Schema(description = "동아리 ID", example = "1")
        @NotNull
        Long clubId,

        @Schema(description = "활동 제목", example = "가츠동 축구 동아리 첫 모임")
        @NotNull
        String title,

        @Schema(description = "활동 설명", example = "가츠동 축구 동아리 첫 모임입니다.")
        @NotNull
        String description,

        @Schema(description = "활동 날짜", example = "2023-08-31")
        @NotNull
        LocalDate date
) implements ClubIdentifiable {
    public Activity toEntity(Club club) {
        return Activity.builder()
                .club(club)
                .title(title())
                .description(description())
                .date(date())
                .build();
    }
}
