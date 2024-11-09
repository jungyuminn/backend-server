package club.gach_dong.exception;

import club.gach_dong.response.status.ErrorCode;

public class FileException {

    public static class FileNameNotFoundException extends DomainException {
        public FileNameNotFoundException() {
            super(ErrorCode.FILE_NAME_NOT_FOUND);
        }
    }

    public static class FileNameTooLongException extends DomainException {
        public FileNameTooLongException() {
            super(ErrorCode.FILE_NAME_TOO_LONG);
        }
    }

    public static class FileTooLargeException extends DomainException {
        public FileTooLargeException() {
            super(ErrorCode.FILE_TOO_LARGE);
        }
    }

    public static class FileFormatNotSupportedException extends DomainException {
        public FileFormatNotSupportedException() {
            super(ErrorCode.FILE_FORMAT_NOT_SUPPORTED);
        }
    }

    public static class FileNameDuplicatedException extends DomainException {
        public FileNameDuplicatedException() {
            super(ErrorCode.FILE_NAME_DUPLICATED);
        }
    }

    public static class FileTooManyException extends DomainException {
        public FileTooManyException() {
            super(ErrorCode.FILE_TOO_MANY);
        }
    }

    public static class FileFormatNotFoundException extends DomainException {
        public FileFormatNotFoundException() {
            super(ErrorCode.FILE_FORMAT_NOT_FOUND);
        }
    }

    public static class FileUploadFailedException extends DomainException {
        public FileUploadFailedException() {
            super(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    public static class FileDeleteFailedException extends DomainException {
        public FileDeleteFailedException() {
            super(ErrorCode.FILE_DELETE_FAILED);
        }
    }

    public static class FileDeleteFailedCriticalException extends DomainException {
        public FileDeleteFailedCriticalException() {
            super(ErrorCode.FILE_DELETE_FAILED_CRITICAL);
        }
    }

    public static class FileNotFoundException extends DomainException {
        public FileNotFoundException() {
            super(ErrorCode.FILE_NOT_FOUND);
        }
    }
}
