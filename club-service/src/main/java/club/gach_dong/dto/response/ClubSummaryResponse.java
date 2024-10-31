package club.gach_dong.dto.response;

import club.gach_dong.domain.Club;
import club.gach_dong.domain.ClubCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ClubSummaryResponse(
        @Schema(description = "동아리 id", example = "1")
        @NotNull
        Long clubId,

        @Schema(description = "동아리 이름", example = "가츠동")
        @NotNull
        String clubName,

        @Schema(description = "동아리 카테고리", example = "SPORTS")
        @NotNull
        ClubCategory category,

        @Schema(description = "한줄 소개", example = "가츠동은 최고의 동아리입니다.")
        @NotNull
        String shortDescription,

        @Schema(description = "이미지 URL", example = "http://example.com/image.jpg")
        @NotNull
        String clubImageUrl,

        @Schema(description = "모집 여부", example = "true")
        @NotNull
        boolean recruitingStatus
) {
    public static ClubSummaryResponse from(Club club) {
        return new ClubSummaryResponse(
                club.getId(),
                club.getName(),
                club.getCategory(),
                club.getShortDescription(),
                club.getClubImageUrl(),
                club.isRecruitingStatus()
        );
    }
}
