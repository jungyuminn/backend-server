package club.gach_dong.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import club.gach_dong.entity.Role;

public record RegistrationRequest(
        @NotBlank(message = "이메일은 필수값입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gachon\\.ac\\.kr$", message = "이메일은 gachon.ac.kr 도메인이어야 합니다.")
        @Size(max = 255, message = "최대 허용 길이를 초과하였습니다.")
        @Schema(description = "사용자 이메일 (gachon.ac.kr 도메인)")
        String email,

        @NotBlank(message = "비밀번호는 필수값입니다.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문, 숫자, 특수문자를 사용하세요.")
        String password,

        @NotBlank(message = "이름은 필수값입니다.")
        @Size(max = 100, message = "최대 허용 길이를 초과하였습니다.")
        String name,

        @NotNull(message = "권한은 필수값입니다.")
        Role role
) {}
