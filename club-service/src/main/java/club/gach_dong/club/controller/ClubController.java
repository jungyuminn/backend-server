package club.gach_dong.club.controller;

import club.gach_dong.club.api.ClubApiSpecification;
import club.gach_dong.club.dto.response.ArrayResponse;
import club.gach_dong.club.dto.response.ClubResponse;
import club.gach_dong.club.dto.response.ClubSummaryResponse;
import club.gach_dong.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClubController implements ClubApiSpecification {

    private final ClubService clubService;

    @Override
    public ArrayResponse<ClubSummaryResponse> getClubs() {
        List<ClubSummaryResponse> clubList = clubService.getAllClubs();
        return ArrayResponse.of(clubList);
    }

    @Override
    public ClubResponse getClub(String clubName) {
        return clubService.getClub(clubName);
    }
}
