package club.gach_dong.dto.response;

import club.gach_dong.domain.Club;
import club.gach_dong.domain.ClubCategory;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ClubResponse(
        @Schema(description = "동아리 ID", example = "1")
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
        boolean recruitingStatus,

        @Schema(description = "동아리 설명", example = "가츠동은 다양한 활동을 하는 동아리입니다.")
        @NotNull
        String introduction,

        @Schema(description = "설립일", example = "2023-01-01T00:00:00")
        @NotNull
        LocalDateTime establishedAt,

        @Schema(description = "업데이트일", example = "2023-01-01T00:00:00")
        @NotNull
        LocalDateTime updatedAt
) {

    public static ClubResponse from(Club club) {
        return new ClubResponse(
                club.getId(),
                club.getName(),
                club.getCategory(),
                club.getShortDescription(),
                club.getClubImageUrl(),
                club.isRecruitingStatus(),
                club.getIntroduction(),
                club.getEstablishedAt(),
                club.getUpdatedAt()
        );
    }
}

