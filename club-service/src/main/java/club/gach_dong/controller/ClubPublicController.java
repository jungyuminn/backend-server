package club.gach_dong.controller;

import club.gach_dong.api.ClubPublicApiSpecification;
import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.ClubActivityResponse;
import club.gach_dong.dto.response.ClubContactInfoResponse;
import club.gach_dong.dto.response.ClubRecruitmentDetailResponse;
import club.gach_dong.dto.response.ClubRecruitmentResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import club.gach_dong.service.ClubReadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClubPublicController implements ClubPublicApiSpecification {

    private final ClubReadService clubReadService;

    @Override
    public ArrayResponse<ClubSummaryResponse> getClubs() {
        List<ClubSummaryResponse> clubList = clubReadService.getAllClubs();
        return ArrayResponse.of(clubList);
    }

    @Override
    public ClubResponse getClub(Long clubId) {
        return clubReadService.getClub(clubId);
    }

    @Override
    public ArrayResponse<ClubActivityResponse> getClubActivities(Long clubId) {
        List<ClubActivityResponse> clubActivityResponse = clubReadService.getClubActivities(clubId);
        return ArrayResponse.of(clubActivityResponse);
    }

    @Override
    public ArrayResponse<ClubContactInfoResponse> getClubContactInfo(Long clubId) {
        List<ClubContactInfoResponse> clubContactInfoResponse = clubReadService.getClubContactInfo(clubId);
        return ArrayResponse.of(clubContactInfoResponse);
    }

    @Override
    public ArrayResponse<ClubRecruitmentResponse> getClubsRecruitments() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<ClubRecruitmentResponse> clubRecruitmentsResponse = clubReadService.getClubsRecruitments(currentTime);
        return ArrayResponse.of(clubRecruitmentsResponse);
    }

    @Override
    public ArrayResponse<ClubRecruitmentDetailResponse> getClubRecruitments(Long clubId) {
        LocalDateTime currentTime = LocalDateTime.now();
        List<ClubRecruitmentDetailResponse> clubRecruitmentDetailResponse = clubReadService.getClubRecruitments(clubId, currentTime);
        return ArrayResponse.of(clubRecruitmentDetailResponse);
    }

    @Override
    public ClubRecruitmentDetailResponse getClubRecruitment(Long clubId, Long recruitmentId, HttpServletRequest request, HttpServletResponse response) {
        LocalDateTime currentTime = LocalDateTime.now();
        return clubReadService.getClubRecruitment(clubId, recruitmentId, request, response, currentTime);
    }
}
