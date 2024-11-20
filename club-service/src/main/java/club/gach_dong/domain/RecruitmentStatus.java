package club.gach_dong.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RecruitmentStatus {
    RECRUITING("모집 중"),
    RECRUITMENT_END("모집 종료");

    private final String text;
}
