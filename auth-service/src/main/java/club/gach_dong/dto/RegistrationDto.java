package club.gach_dong.dto;

import jakarta.persistence.Table;
import lombok.Data;

@Getter
@Table(name = "users")
public class RegistrationDto {
    private String email;
    private String code;
    private String password;
    private String name;
    private String role;
}
