package club.gach_dong.response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    REDIRECT_URI_NOT_FOUND("COMMON0001", "리다이렉트 URI를 찾을 수 없습니다"),
    REQUEST_INPUT_NOT_VALID("COMMON0002", "입력 값이 올바르지 않습니다."),

    _INTERNAL_SERVER_ERROR("COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST("COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED("COMMON401", "인증이 필요합니다."),
    _FORBIDDEN("COMMON403", "금지된 요청입니다."),
    _EMPTY_FIELD("COMMON404", "입력 값이 누락되었습니다."),

    CLUB_UNAUTHORIZED("CLUBADMIN401", "인증이 필요합니다."),

    USER_NOT_FOUND("USER401", "사용자 ID를 찾을 수 없습니다."),

    APPLICATION_FORM_NOT_FOUND("APPLICATIONFORM401", "ApplicationForm이 없습니다."),
    APPLICATION_FORM_IN_USE("APPLICATIONFORM402", "이미 사용중인 지원서 양식입니다."),
    APPLICATION_DUPLICATED("APPLICATION401", "신청이 중복되었습니다."),
    APPLICATION_NOT_FOUND("APPLICATION402", "존재하지 않는 신청입니다."),
    APPLICATION_UNAUTHORIZED("APPLICATION403", "인증이 필요합니다."),
    APPLICATION_NOT_CHANGEABLE("APPLICATION404", "해당 신청의 상태를 변경할 수 없습니다."),

    FILE_NAME_NOT_FOUND("FILE401", "업로드한 파일의 이름이 없습니다."),
    FILE_NAME_TOO_LONG("FILE402", "업로드한 파일의 이름 너무 깁니다."),
    FILE_TOO_LARGE("FILE403", "업로드한 파일의 전체 크기가 너무 큽니다."),
    FILE_FORMAT_NOT_SUPPORTED("FILE401", "업로드한 파일의 형식이 잘못 되었습니다."),
    FILE_NAME_DUPLICATED("FILE405", "업로드한 파일 이름이 중복됩니다."),
    FILE_TOO_MANY("FILE406", "업로드한 파일이 너무 많습니다."),
    FILE_FORMAT_NOT_FOUND("FILE407", "파일의 형식을 찾을 수 없습니다."),
    FILE_UPLOAD_FAILED("FILE408", "파일 업로드에 실패했습니다."),
    FILE_DELETE_FAILED("FILE409", "파일 삭제에 실패했습니다. (Delete 함수 false 반환)"),
    FILE_DELETE_FAILED_CRITICAL("FILE410", "파일 삭제에 실패했습니다. (권한 등의 기타 오류)"),
    FILE_NOT_FOUND("FILE411", "파일이 존재하지 않습니다.");

    private final String code;
    private final String message;
}