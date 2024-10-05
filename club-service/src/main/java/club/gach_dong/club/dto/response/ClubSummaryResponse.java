package club.gach_dong.club.dto.response;

import club.gach_dong.club.domain.Club;
import club.gach_dong.club.domain.ClubCategory;
import io.swagger.v3.oas.annotations.media.Schema;

public record ClubSummaryResponse(
        @Schema(description = "동아리 이름", example = "가츠동")
        String clubName,

        @Schema(description = "동아리 카테고리", example = "SPORTS")
        ClubCategory category,

        @Schema(description = "한줄 소개", example = "가츠동은 최고의 동아리입니다.")
        String shortDescription,

        @Schema(description = "이미지 URL", example = "http://example.com/image.jpg")
        String clubImageUrl,

        @Schema(description = "모집 여부", example = "true")
        boolean recruitingStatus
) {
    public static ClubSummaryResponse from(Club club) {
        return new ClubSummaryResponse(
                club.getName(),
                club.getCategory(),
                club.getShortDescription(),
                club.getClubImageUrl(),
                club.isRecruitingStatus()
        );
    }
}
