package club.gach_dong.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserRequest(
        @Schema(description = "사용자 이메일", example = "user@gachon.ac.kr")
        String email,

        @Schema(description = "비밀번호", example = "password123!")
        String password,

        @Schema(description = "사용자 이름", example = "홍길동")
        String name,

        @Schema(description = "사용자 권한", example = "ADMIN")
        String role
) {}
