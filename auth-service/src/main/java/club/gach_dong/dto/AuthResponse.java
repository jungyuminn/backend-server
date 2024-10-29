package club.gach_dong.dto;

import lombok.Getter;

@Getter
public class AuthResponse {
    private String token;
    private String message;

    private AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public static AuthResponse of(String token) {
        return new AuthResponse(token, "로그인 성공");
    }

    public static AuthResponse withMessage(String message) {
        return new AuthResponse(null, message);
    }
}