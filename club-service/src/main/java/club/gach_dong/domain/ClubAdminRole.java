package club.gach_dong.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ClubAdminRole {
    PRESIDENT("동아리 회장"),
    MEMBER("일반 관리자");

    private final String text;
}
