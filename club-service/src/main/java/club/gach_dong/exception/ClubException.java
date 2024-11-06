package club.gach_dong.exception;

public class ClubException {
    public static class ClubNotFoundException extends DomainException {
        public ClubNotFoundException() {
            super(ErrorCode.CLUB_NOT_FOUND);
        }
    }
}
