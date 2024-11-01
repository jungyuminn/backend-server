package club.gach_dong.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChangePasswordRequest(
        @Schema(description = "현재 비밀번호", example = "currentpassword123")
        String currentPassword,

        @Schema(description = "새 비밀번호", example = "newpassword123!")
        String newPassword
) {}