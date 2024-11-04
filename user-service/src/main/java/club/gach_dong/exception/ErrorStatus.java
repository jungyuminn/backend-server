package club.gach_dong.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.");

    private final String message;
}
