package club.gach_dong.exception;

import club.gach_dong.response.status.ErrorCode;

public class RedirectUriNotFoundException extends DomainException {
    public RedirectUriNotFoundException() {
        super(ErrorCode.REDIRECT_URI_NOT_FOUND);
    }
}