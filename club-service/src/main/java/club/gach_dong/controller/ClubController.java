package club.gach_dong.controller;

import club.gach_dong.api.ClubApiSpecification;
import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import club.gach_dong.service.ClubService;
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
