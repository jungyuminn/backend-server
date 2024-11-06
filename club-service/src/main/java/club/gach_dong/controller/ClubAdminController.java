package club.gach_dong.controller;

import club.gach_dong.api.ClubAdminApiSpecification;
import club.gach_dong.dto.request.CreateClubActivityRequest;
import club.gach_dong.dto.request.CreateClubContactInfoRequest;
import club.gach_dong.dto.request.CreateClubRecruitmentRequest;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClubAdminController implements ClubAdminApiSpecification {
    private final ClubService clubService;

    @Override
    public ResponseEntity<ClubResponse> createClub(CreateClubRequest createClubRequest) {
        ClubResponse clubResponse = clubService.createClub(createClubRequest);
        return new ResponseEntity<>(clubResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CreateClubActivityRequest> createClubActivity(
            CreateClubActivityRequest createClubActivityRequest) {
        return null;
    }

    @Override
    public ResponseEntity<CreateClubContactInfoRequest> createClubContactInfo(
            CreateClubContactInfoRequest createClubContactInfoRequest) {
        return null;
    }

    @Override
    public ResponseEntity<CreateClubRecruitmentRequest> createClubRecruitment(
            CreateClubRecruitmentRequest createClubRecruitmentRequest) {
        return null;
    }

}
