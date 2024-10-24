package club.gach_dong.controller;

import club.gach_dong.api.ClubApiSpecification;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.ClubActivityResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import club.gach_dong.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ClubResponse getClub(String clubId) {
        return clubService.getClub(clubId);
    }

    @Override
    public ResponseEntity<ClubResponse> createClub(CreateClubRequest createClubRequest) {
        ClubResponse clubResponse = clubService.createClub(createClubRequest);
        return new ResponseEntity<>(clubResponse, HttpStatus.CREATED);
    }

    @Override
    public ArrayResponse<ClubActivityResponse> getClubActivities(String clubId) {
        List<ClubActivityResponse> clubActivityResponse = clubService.getClubActivities(clubId);
        return ArrayResponse.of(clubActivityResponse);
    }
}
