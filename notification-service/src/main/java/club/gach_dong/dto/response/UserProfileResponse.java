package club.gach_dong.service;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserProfileResponse(
        @Schema(description = "사용자 이메일", example = "user@gachon.ac.kr")
        String email,

        @Schema(description = "사용자 이름", example = "홍길동")
        String name,

        @Schema(description = "사용자 권한", example = "USER, ADMIN")
        String role
) {}
