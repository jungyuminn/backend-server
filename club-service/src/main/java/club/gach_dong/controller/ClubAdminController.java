package club.gach_dong.controller;

import club.gach_dong.api.ClubAdminApiSpecification;
import club.gach_dong.dto.request.CreateClubActivityRequest;
import club.gach_dong.dto.request.CreateClubContactInfoRequest;
import club.gach_dong.dto.request.CreateClubRecruitmentRequest;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.request.UpdateClubActivityRequest;
import club.gach_dong.dto.request.UpdateClubRequest;
import club.gach_dong.dto.request.UpdateContactInfoRequest;
import club.gach_dong.dto.response.AdminAuthorizedClubResponse;
import club.gach_dong.dto.response.AdminInfoResponse;
import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.AutorizeAdminResponse;
import club.gach_dong.dto.response.ContactInfoResponse;
import club.gach_dong.dto.response.CreateClubActivityResponse;
import club.gach_dong.dto.response.CreateClubContactInfoResponse;
import club.gach_dong.dto.response.CreateClubRecruitmentResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.UpdateClubActivityResponse;
import club.gach_dong.service.ClubReadService;
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
    private final ClubReadService clubReadService;

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
        List<AdminAuthorizedClubResponse> authorizedClubs = clubReadService.getAuthorizedClubs(userReferenceId);
        return ArrayResponse.of(authorizedClubs);
    }

    @Override
    public Boolean hasAuthority(String userReferenceId, Long clubId) {
        return clubReadService.hasAuthority(userReferenceId, clubId);
    }

    @Override
    public Boolean hasAuthorityByRecruitmentId(String userReferenceId, Long recruitmentId) {
        return clubReadService.hasAuthorityByRecruitmentId(userReferenceId, recruitmentId);
    }

    @Override
    public Boolean isValidRecruitment(Long recruitmentId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return clubReadService.isValidRecruitment(recruitmentId, currentDateTime);
    }

    @Override
    public ResponseEntity<AutorizeAdminResponse> authorizeAdmin(String userReferenceId, Long clubId) {
        clubService.authorizeAdmin(userReferenceId, clubId);
        return new ResponseEntity<>(new AutorizeAdminResponse(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClubResponse> updateClubInfo(String userReferenceId, UpdateClubRequest updateClubRequest) {
        ClubResponse updateClubResponse = clubService.updateClubInfo(userReferenceId, updateClubRequest);
        return new ResponseEntity<>(updateClubResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ContactInfoResponse> updateContactInfo(String userReferenceId,
                                                                 UpdateContactInfoRequest updateContactInfoRequest) {
        ContactInfoResponse updateContactInfoResponse = clubService.updateContactInfo(userReferenceId, updateContactInfoRequest);
        return new ResponseEntity<>(updateContactInfoResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UpdateClubActivityResponse> updateClubActivity(String userReferenceId, UpdateClubActivityRequest updateClubActivityRequest) {
        UpdateClubActivityResponse updateClubActivityResponse = clubService.updateClubActivity(userReferenceId, updateClubActivityRequest);
        return new ResponseEntity<>(updateClubActivityResponse, HttpStatus.OK);
    }

    @Override
    public ArrayResponse<AdminInfoResponse> getAdmins(String userReferenceId, Long clubId) {
        List<AdminInfoResponse> admins = clubReadService.getAdmins(userReferenceId, clubId);
        return ArrayResponse.of(admins);
    }

}
