package club.gach_dong.exception;

import club.gach_dong.response.status.ErrorCode;

public class ClubException {

    public static class ClubAdminUnauthorizedException extends DomainException {
        public ClubAdminUnauthorizedException() {
            super(ErrorCode.CLUB_UNAUTHORIZED);
        }
    }
}
