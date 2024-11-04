package club.gach_dong.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ClubCategory {
    ACADEMIC("학술 · 사회"),
    EXHIBITION("전시 · 취미"),
    SPORTS("체육"),
    PERFORMANCE("공연"),
    MUSIC("음악"),
    RELIGION("종교"),
    OTHER("기타");

    private final String text;
}
