package club.gach_dong.exception;

import club.gach_dong.response.status.ErrorCode;

public class UserException {

    public static class UserNotFound extends DomainException {
        public UserNotFound() {
            super(ErrorCode.USER_NOT_FOUND);
        }
    }
}