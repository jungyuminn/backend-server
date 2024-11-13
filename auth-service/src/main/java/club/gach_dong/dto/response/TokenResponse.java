package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenResponse(
        @Schema(description = "JWT Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,

        @Schema(description = "응답 메시지", example = "Access Token 재발급 성공")
        String message
) {
    public static TokenResponse of(String accessToken) {
        return new TokenResponse(accessToken, "Access Token 재발급 성공");
    }

    public static TokenResponse withMessage(String message) {
        return new TokenResponse(null, message);
    }
}