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
        List<ClubRecruitmentResponse> clubRecruitmentsResponse = clubReadService.getClubsRecruitments();
        return ArrayResponse.of(clubRecruitmentsResponse);
    }

    @Override
    public ArrayResponse<ClubRecruitmentDetailResponse> getClubRecruitments(Long clubId) {
        List<ClubRecruitmentDetailResponse> clubRecruitmentDetailResponse = clubReadService.getClubRecruitments(clubId);
        return ArrayResponse.of(clubRecruitmentDetailResponse);
    }

    @Override
    public ClubRecruitmentDetailResponse getClubRecruitment(Long clubId, Long recruitmentId) {
        return clubReadService.getClubRecruitment(clubId, recruitmentId);
    }
}
