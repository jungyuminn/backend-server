package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UserProfileResponse(
        @Schema(description = "사용자 이메일", example = "user@gachon.ac.kr", nullable = false)
        @NotNull
        String email,

        @Schema(description = "사용자 이름", example = "홍길동", nullable = false)
        @NotNull
        String name,

        @Schema(description = "사용자 권한", example = "USER, ADMIN", nullable = false)
        @NotNull
        String role
) {}
