package club.gach_dong.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull
        @Schema(description = "사용자 이메일", example = "user@gachon.ac.kr")
        String email,

        @NotNull
        @Schema(description = "비밀번호", example = "password123!")
        String password
) {}
