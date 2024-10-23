package club.gach_dong.response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InSuccess {

    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

    APPLICATION_FORM_CREATED(HttpStatus.OK, "APPLICATIONFORM200", "지원서 양식 저장에 성공했습니다."),
    APPLICATION_FORM_GET_ADMIN_INFO(HttpStatus.OK, "APPLICATIONFORM201", "관리자용 지원서 양식 조회에 성공했습니다."),
    APPLICATION_FORM_GET_USER_INFO(HttpStatus.OK, "APPLICATIONFORM202", "사용자용 지원서 양식 조회에 성공했습니다."),
    APPLICATION_FORM_DELETED(HttpStatus.OK, "APPLICATIONFORM203", "지원서 양식 삭제에 성공했습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
