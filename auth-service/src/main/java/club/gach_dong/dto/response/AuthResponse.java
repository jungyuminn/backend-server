package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponse(
        @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,

        @Schema(description = "응답 메시지", example = "로그인 성공")
        String message
) {
    public static AuthResponse of(String token) {
        return new AuthResponse(token, "로그인 성공");
    }

    public static AuthResponse withMessage(String message) {
        return new AuthResponse(null, message);
    }
}
