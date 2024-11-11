package club.gach_dong.exception;

public class NotificationException {
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
}
