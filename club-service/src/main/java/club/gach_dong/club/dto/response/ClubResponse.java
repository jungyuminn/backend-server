package club.gach_dong.club.dto.response;

import club.gach_dong.club.domain.Club;
import club.gach_dong.club.domain.ClubCategory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ClubResponse(
        @Schema(description = "동아리 이름", example = "가츠동")
        String clubName,

        @Schema(description = "동아리 카테고리", example = "SPORTS")
        ClubCategory category,

        @Schema(description = "한줄 소개", example = "가츠동은 최고의 동아리입니다.")
        String shortDescription,

        @Schema(description = "이미지 URL", example = "http://example.com/image.jpg")
        String clubImageUrl,

        @Schema(description = "모집 여부", example = "true")
        boolean recruitingStatus,

        @Schema(description = "동아리 설명", example = "가츠동은 다양한 활동을 하는 동아리입니다.")
        String description,

        @Schema(description = "설립일", example = "2023-01-01T00:00:00")
        LocalDateTime establishedAt,

        @Schema(description = "업데이트일", example = "2023-01-01T00:00:00")
        LocalDateTime updatedAt
) {
    public static ClubResponse from(Club club) {
        return new ClubResponse(
                club.getName(),
                club.getCategory(),
                club.getShortDescription(),
                club.getClubImageUrl(),
                club.isRecruitingStatus(),
                club.getDescription(),
                club.getEstablishedAt(),
                club.getUpdatedAt()
        );
    }
}