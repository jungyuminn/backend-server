package club.gach_dong.dto.request;

import club.gach_dong.domain.ClubCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateClubRequest(

        @Schema(description = "동아리 이름", example = "가츠동")
        @NotBlank
        String name,

        @Schema(description = "동아리 카테고리", example = "SPORTS")
        @NotNull
        ClubCategory category,

        @Schema(description = "동아리 한줄 설명", example = "가츠동은 최고의 동아리입니다.")
        @NotBlank
        String shortDescription,

        @Schema(description = "동아리 소개", example = "<h1>가츠동</h1> <p>최고의 동아리입니다</p>")
        String introduction,

        @Schema(description = "동아리 이미지 URL", example = "http://example.com/image.png")
        String clubImageUrl,

        @Schema(description = "동아리 설립일", example = "2023-01-01T00:00:00")
        LocalDateTime establishedAt
) {
}
