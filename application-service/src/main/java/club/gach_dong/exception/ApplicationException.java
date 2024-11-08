package club.gach_dong.exception;

import club.gach_dong.response.status.ErrorCode;

public class ApplicationException {
    public static class ApplicationFormNotFoundException extends DomainException {
        public ApplicationFormNotFoundException() {
            super(ErrorCode.APPLICATION_FORM_NOT_FOUND);
        }
    }

    public static class ApplicationFormInUseException extends DomainException {
        public ApplicationFormInUseException() {
            super(ErrorCode.APPLICATION_FORM_IN_USE);
        }
    }

    public static class ApplicationDuplicatedException extends DomainException {
        public ApplicationDuplicatedException() {
            super(ErrorCode.APPLICATION_DUPLICATED);
        }
    }

    public static class ApplicationNotFoundException extends DomainException {
        public ApplicationNotFoundException() {
            super(ErrorCode.APPLICATION_NOT_FOUND);
        }
    }

    public static class ApplicationUnauthorizedException extends DomainException {
        public ApplicationUnauthorizedException() {
            super(ErrorCode.APPLICATION_UNAUTHORIZED);
        }
    }

    public static class ApplicationNotChangeableException extends DomainException {
        public ApplicationNotChangeableException() {
            super(ErrorCode.APPLICATION_NOT_CHANGEABLE);
        }
    }

}
