package club.gach_dong.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    REDIRECT_URI_NOT_FOUND("COMMON0001", "리다이렉트 URI를 찾을 수 없습니다"),
    REQUEST_INPUT_NOT_VALID("COMMON0002", "입력 값이 올바르지 않습니다."),

    _INTERNAL_SERVER_ERROR("COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST("COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED("COMMON401", "인증이 필요합니다."),
    _FORBIDDEN("COMMON403", "금지된 요청입니다."),
    _EMPTY_FIELD("COMMON404", "입력 값이 누락되었습니다."),

    CLUB_NOT_FOUND("CLUB0001", "동아리를 찾을 수 없습니다"),
    CLUB_UNAUTHORIZED("CLUBADMIN401", "인증이 필요합니다."),

    GACHDONG_USER_SERVICE_FAILED("SERVICE0001", "가츠동의 user-service 호출이 실패했습니다"),

    USER_NOT_FOUND("USER401", "사용자 ID를 찾을 수 없습니다."),

    RECRUITMENT_NOT_FOUND("RECRUITMENT001", "모집 공고를 찾을 수 없습니다."),

    CONTACT_INFO_NOT_FOUND("CONTACT001", "연락처 정보를 찾을 수 없습니다.");

    private final String code;
    private final String message;
}