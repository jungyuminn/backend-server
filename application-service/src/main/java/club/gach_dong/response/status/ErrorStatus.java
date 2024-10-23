package club.gach_dong.response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorStatus {
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _EMPTY_FIELD(HttpStatus.NO_CONTENT, "COMMON404", "입력 값이 누락되었습니다."),
    
    CLUB_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "APPLICATION401", "인증이 필요합니다."),

    APPLICATION_FORM_NOT_FOUND(HttpStatus.NOT_FOUND, "APPLICATIONFORM401", "ApplicationForm이 없습니다."),
    APPLICATION_FORM_IN_USE(HttpStatus.CONFLICT, "APPLICATIONFORM402", "이미 사용중인 지원서 양식입니다."),

    APPLICATION_DUPLICATED(HttpStatus.CONFLICT, "APPLICATION401", "신청이 중복되었습니다."),



    //  파일 업로드 관련
    FILE_NAME_NOT_FOUND(HttpStatus.BAD_REQUEST, "FILE4001", "업로드한 문서의 이름이 없습니다."),
    FILE_NAME_TOO_LONG(HttpStatus.BAD_REQUEST, "FILE4002", "업로드한 문서의 이름 너무 깁니다."),
    FILE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "FILE4003", "업로드한 문서의 전체 크기가 너무 큽니다."),
    FILE_FORMAT_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "FILE4001", "업로드한 파일의 형식이 잘못 되었습니다."),
    FILE_NAME_DUPLICATED(HttpStatus.CONFLICT, "FILE4005", "업로드한 파일 이름이 중복됩니다."),
    FILE_TOO_MANY(HttpStatus.PAYLOAD_TOO_LARGE, "FILE4006", "업로드한 파일이 너무 많습니다."),
    ;
    //
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
