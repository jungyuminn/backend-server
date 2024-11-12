package club.gach_dong.exception;

public class NotificationException {
    public static class AuthAPIFailedException extends DomainException {
        public AuthAPIFailedException() {
            super(ErrorCode.GACHDONG_AUTH_SERVICE_FAILED);
        }
    }

    public static class NotSupportedPublishTypeException extends DomainException {
        public NotSupportedPublishTypeException() {
            super(ErrorCode.NOT_SUPPORTED_PUBLISH_TYPE);
        }
    }
}
