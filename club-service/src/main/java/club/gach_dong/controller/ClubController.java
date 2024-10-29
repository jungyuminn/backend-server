package club.gach_dong.controller;

import club.gach_dong.api.ClubApiSpecification;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.*;
import club.gach_dong.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ArrayResponse<ClubContactInfoResponse> getClubContactInfo(String clubId) {
        List<ClubContactInfoResponse> clubContactInfoResponse = clubService.getClubContactInfo(clubId);
        return ArrayResponse.of(clubContactInfoResponse);
    }
}
