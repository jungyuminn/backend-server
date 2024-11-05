package club.gach_dong.domain;

import static club.gach_dong.domain.ClubCategory.ACADEMIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecruitmentTest {

    @DisplayName("조회 시점의 날짜를 기준으로 모집 마감 기간을 계산한다.")
    @Test
    void calculateRemainingDays() {
        // given
        Club club = Club.of("GDSC Gachon", ACADEMIC, "GDSC Gachon 소개", "GDSC Gachon 로고", "GDSC Gachon 이미지", LocalDateTime.of(2024, 3, 1, 0,0,0));
        Recruitment recruitment = Recruitment.of("GDSC Gachon 2024 멤버 모집", LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 15), club);
        // when
        Long remainingDays = recruitment.calculateRemainingDays(LocalDate.of(2024, 3, 10));
        // then
        assertThat(remainingDays).isEqualTo(5);
    }
}