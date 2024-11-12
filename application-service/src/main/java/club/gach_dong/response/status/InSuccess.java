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
    APPLICATION_FORM_CHANGED(HttpStatus.OK, "APPLICATIONFORM204", "지원서 양식 수정에 성공했습니다."),

    APPLICATION_SUCCESS(HttpStatus.OK, "APPLICATION201", "신청에 성공했습니다."),
    APPLICATION_DELETED(HttpStatus.OK, "APPLICATION202", "신청 취소에 성공했습니다."),
    APPLICATION_CHANGED(HttpStatus.OK, "APPLICATION203", "신청 수정에 성공했습니다."),
    APPLICATION_HISTORY_GET_SUCCESS(HttpStatus.OK, "APPLICATION204", "지원 내역 조회에 성공했습니다."),
    APPLICATION_STATUS_CHANGED(HttpStatus.OK, "APPLICATION205", "지원 상태 수정에 성공했습니다."),
    APPLICATION_TEMP_STATUS_GET_SUCCESS(HttpStatus.OK, "APPLICATION206", "해당 지원의 임시저장 정보 확인에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
