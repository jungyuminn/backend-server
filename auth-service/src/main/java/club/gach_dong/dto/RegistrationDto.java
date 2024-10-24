package club.gach_dong.dto;

import jakarta.persistence.Table;
import lombok.Data;

@Getter
@Table(name = "users")
public class RegistrationDto {
    @NotBlank(message = "이메일은 필수값입니다.")
    private String email;

    @NotBlank(message = "인증코드는 필수값입니다.")
    private String code;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "이름은 필수값입니다.")
    private String name;

    @NotBlank(message = "권한은 필수값입니다.")
    private String role;
}