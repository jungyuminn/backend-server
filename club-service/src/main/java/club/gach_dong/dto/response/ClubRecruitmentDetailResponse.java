package club.gach_dong.dto.response;

import club.gach_dong.domain.Club;
import club.gach_dong.domain.Recruitment;
import java.time.LocalDateTime;

public record ClubRecruitmentDetailResponse(
        Long clubId,
        Long recruitmentId,
        String title,
        String content,
        boolean recruitmentStatus,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
    public static ClubRecruitmentDetailResponse of(Club club, Recruitment recruitment) {
        return new ClubRecruitmentDetailResponse(
                club.getId(),
                recruitment.getId(),
                recruitment.getTitle(),
                recruitment.getContent(),
                recruitment.isRecruitmentStatus(),
                recruitment.getStartDate(),
                recruitment.getEndDate()
        );
    }
}
