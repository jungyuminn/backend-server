package club.gach_dong.controller;

import club.gach_dong.api.ClubAdminApiSpecification;
import club.gach_dong.dto.request.CreateClubActivityRequest;
import club.gach_dong.dto.request.CreateClubContactInfoRequest;
import club.gach_dong.dto.request.CreateClubRecruitmentRequest;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.AdminAuthorizedClubResponse;
import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.CreateClubActivityResponse;
import club.gach_dong.dto.response.CreateClubContactInfoResponse;
import club.gach_dong.dto.response.CreateClubRecruitmentResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.service.ClubService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClubAdminController implements ClubAdminApiSpecification {
    private final ClubService clubService;

    @Override
    public ResponseEntity<ClubResponse> createClub(
            String userReferenceId,
            CreateClubRequest createClubRequest
    ) {
        ClubResponse createClubResponse = clubService.createClub(userReferenceId, createClubRequest);
        return new ResponseEntity<>(createClubResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CreateClubActivityResponse> createClubActivity(
            String userReferenceId,
            CreateClubActivityRequest createClubActivityRequest
    ) {
        CreateClubActivityResponse createClubActivityResponse = clubService.createClubActivity(
                userReferenceId,
                createClubActivityRequest
        );
        return new ResponseEntity<>(createClubActivityResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CreateClubContactInfoResponse> createClubContactInfo(
            String userReferenceId,
            CreateClubContactInfoRequest createClubContactInfoRequest
    ) {
        CreateClubContactInfoResponse createClubContactInfoResponse = clubService.createClubContactInfo(
                userReferenceId,
                createClubContactInfoRequest
        );
        return new ResponseEntity<>(createClubContactInfoResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CreateClubRecruitmentResponse> createClubRecruitment(
            String userReferenceId,
            CreateClubRecruitmentRequest createClubRecruitmentRequest
    ) {
        CreateClubRecruitmentResponse createClubRecruitmentResponse = clubService.createClubRecruitment(
                userReferenceId,
                createClubRecruitmentRequest
        );
        return new ResponseEntity<>(createClubRecruitmentResponse, HttpStatus.CREATED);
    }

    @Override
    public ArrayResponse<AdminAuthorizedClubResponse> getAuthorizedClubs(String userReferenceId) {
        List<AdminAuthorizedClubResponse> authorizedClubs = clubService.getAuthorizedClubs(userReferenceId);
        return ArrayResponse.of(authorizedClubs);
    }

    @Override
    public Boolean hasAuthority(String userReferenceId, Long clubId) {
        return clubService.hasAuthority(userReferenceId, clubId);
    }

    @Override
    public Boolean isValidRecruitment(Long recruitmentId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return clubService.isValidRecruitment(recruitmentId, currentDateTime);
    }


}
