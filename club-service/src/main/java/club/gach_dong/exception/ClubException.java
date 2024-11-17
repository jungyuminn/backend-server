package club.gach_dong.exception;

public class ClubException {
    public static class RecruitmentNotFoundException extends DomainException {
        public RecruitmentNotFoundException() {
            super(ErrorCode.RECRUITMENT_NOT_FOUND);
        }
    }

    public static class ClubNotFoundException extends DomainException {
        public ClubNotFoundException() {
            super(ErrorCode.CLUB_NOT_FOUND);
        }
    }

    public static class ContactInfoNotFoundException extends DomainException {
        public ContactInfoNotFoundException() {
            super(ErrorCode.CONTACT_INFO_NOT_FOUND);
        }
    }

    public static class ActivityNotFoundException extends DomainException {
        public ActivityNotFoundException() {
            super(ErrorCode.ACTIVITY_NOT_FOUND);
        }
    }

    public static class ClubAdminNotFoundException extends DomainException {
        public ClubAdminNotFoundException() {
            super(ErrorCode.CLUB_ADMIN_NOT_FOUND);
        }
    }

    public static class ClubAdminAccessDeniedException extends DomainException {
        public ClubAdminAccessDeniedException() {
            super(ErrorCode.CLUB_ADMIN_ACCESS_DENIED);
        }
    }
}
