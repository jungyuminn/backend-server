package club.gach_dong.controller;

import club.gach_dong.api.ClubPublicApiSpecification;
import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.ClubActivityResponse;
import club.gach_dong.dto.response.ClubContactInfoResponse;
import club.gach_dong.dto.response.ClubRecruitmentDetailResponse;
import club.gach_dong.dto.response.ClubRecruitmentResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import club.gach_dong.service.ClubService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClubPublicController implements ClubPublicApiSpecification {

    private final ClubService clubService;

    @Override
    public ArrayResponse<ClubSummaryResponse> getClubs() {
        List<ClubSummaryResponse> clubList = clubService.getAllClubs();
        return ArrayResponse.of(clubList);
    }

    @Override
    public ClubResponse getClub(Long clubId) {
        return clubService.getClub(clubId);
    }

    @Override
    public ArrayResponse<ClubActivityResponse> getClubActivities(Long clubId) {
        List<ClubActivityResponse> clubActivityResponse = clubService.getClubActivities(clubId);
        return ArrayResponse.of(clubActivityResponse);
    }

    @Override
    public ArrayResponse<ClubContactInfoResponse> getClubContactInfo(Long clubId) {
        List<ClubContactInfoResponse> clubContactInfoResponse = clubService.getClubContactInfo(clubId);
        return ArrayResponse.of(clubContactInfoResponse);
    }

    @Override
    public ArrayResponse<ClubRecruitmentResponse> getClubRecruitments() {
        List<ClubRecruitmentResponse> clubRecruitmentsResponse = clubService.getClubRecruitmentList();
        return ArrayResponse.of(clubRecruitmentsResponse);
    }

    @Override
    public ArrayResponse<ClubRecruitmentDetailResponse> getClubRecruitments(Long clubId) {
        List<ClubRecruitmentDetailResponse> clubRecruitmentDetailResponse = clubService.getClubRecruitment(clubId);
        return ArrayResponse.of(clubRecruitmentDetailResponse);
    }
}
