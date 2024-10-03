package club.gach_dong.club.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClubResponse(
        @Schema(
                description = "동아리 이름",
                example = "가츠동"
        )
        String clubName
) {
    public static ClubResponse of(String clubName) {
        return new ClubResponse(clubName);
    }
}
