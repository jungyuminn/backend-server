package club.gach_dong.controller;

import club.gach_dong.api.ClubApiSpecification;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.*;
import club.gach_dong.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClubController implements ClubApiSpecification {

    private final ClubService clubService;

    @Override
    public ResponseEntity<ClubResponse> createClub(CreateClubRequest createClubRequest) {
        ClubResponse clubResponse = clubService.createClub(createClubRequest);
        return new ResponseEntity<>(clubResponse, HttpStatus.CREATED);
    }
}
