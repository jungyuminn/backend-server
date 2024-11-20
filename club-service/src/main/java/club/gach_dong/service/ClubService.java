package club.gach_dong.service;

import static club.gach_dong.exception.ClubException.*;

import club.gach_dong.annotation.AdminAuthorizationCheck;
import club.gach_dong.domain.Activity;
import club.gach_dong.domain.Club;
import club.gach_dong.domain.ClubAdmin;
import club.gach_dong.domain.ClubAdminRole;
import club.gach_dong.domain.ContactInfo;
import club.gach_dong.domain.Recruitment;
import club.gach_dong.dto.request.CreateClubActivityRequest;
import club.gach_dong.dto.request.CreateClubContactInfoRequest;
import club.gach_dong.dto.request.CreateClubRecruitmentRequest;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.request.UpdateClubActivityRequest;
import club.gach_dong.dto.request.UpdateClubRequest;
import club.gach_dong.dto.request.UpdateContactInfoRequest;
import club.gach_dong.dto.response.ContactInfoResponse;
import club.gach_dong.dto.response.CreateClubActivityResponse;
import club.gach_dong.dto.response.CreateClubContactInfoResponse;
import club.gach_dong.dto.response.CreateClubRecruitmentResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.UpdateClubActivityResponse;
import club.gach_dong.exception.ClubException.ClubNotFoundException;
import club.gach_dong.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubService {

    private final ClubRepository clubRepository;

    // admin
    public ClubResponse createClub(String userReferenceId, CreateClubRequest createClubRequest) {

        Club club = createClubRequest.toEntity(userReferenceId);
        Club savedClub = clubRepository.save(club);

        return ClubResponse.of(savedClub);
    }

    @AdminAuthorizationCheck(role = {ClubAdminRole.PRESIDENT, ClubAdminRole.MEMBER})
    public CreateClubActivityResponse createClubActivity(
            String userReferenceId,
            CreateClubActivityRequest createClubActivityRequest
    ) {
        Club club = clubRepository.findById(createClubActivityRequest.clubId())
                .orElseThrow(ClubNotFoundException::new);

        Activity activity = createClubActivityRequest.toEntity(club);

        club.addActivity(activity);
        clubRepository.save(club);

        Activity savedActivity = club.getActivities().stream()
                .filter(a -> a.getTitle().equals(createClubActivityRequest.title()))
                .findFirst()
                .orElseThrow(ActivityNotFoundException::new);

        return CreateClubActivityResponse.of(savedActivity);
    }

    @AdminAuthorizationCheck(role = {ClubAdminRole.PRESIDENT, ClubAdminRole.MEMBER})
    public CreateClubContactInfoResponse createClubContactInfo(
            String userReferenceId,
            CreateClubContactInfoRequest createClubContactInfoRequest
    ) {
        Club club = clubRepository.findById(createClubContactInfoRequest.clubId())
                .orElseThrow(ClubNotFoundException::new);
        ContactInfo contactInfo = createClubContactInfoRequest.toEntity(club);

        club.addContactInfo(contactInfo);
        clubRepository.save(club);

        ContactInfo savedContactInfo = club.getContactInfo().stream()
                .filter(c -> c.getContactValue().equals(createClubContactInfoRequest.contact()))
                .findFirst()
                .orElseThrow(ContactInfoNotFoundException::new);

        return CreateClubContactInfoResponse.of(savedContactInfo);
    }

    @AdminAuthorizationCheck(role = {ClubAdminRole.PRESIDENT, ClubAdminRole.MEMBER})
    public CreateClubRecruitmentResponse createClubRecruitment(
            String userReferenceId,
            CreateClubRecruitmentRequest createClubRecruitmentRequest
    ) {
        Club club = clubRepository.findById(createClubRecruitmentRequest.clubId())
                .orElseThrow(ClubNotFoundException::new);

        Recruitment recruitment = createClubRecruitmentRequest.toEntity(club);

        club.addRecruitment(recruitment);
        clubRepository.save(club);

        // TODO: recruitment title is not unique
        Recruitment savedRecruitment = club.getRecruitment().stream()
                .filter(r -> r.getTitle().equals(createClubRecruitmentRequest.title()))
                .findFirst()
                .orElseThrow(RecruitmentNotFoundException::new);

        return CreateClubRecruitmentResponse.of(savedRecruitment);
    }

    @AdminAuthorizationCheck(role = {ClubAdminRole.PRESIDENT, ClubAdminRole.MEMBER})
    public ClubResponse updateClubInfo(String userReferenceId, UpdateClubRequest updateClubRequest) {

        Club club = clubRepository.findById(updateClubRequest.clubId())
                .orElseThrow(ClubNotFoundException::new);

        updateClubRequest.updateToEntity(club);

        Club updateClub = clubRepository.save(club);

        return ClubResponse.of(updateClub);
    }

    @AdminAuthorizationCheck(role = {ClubAdminRole.PRESIDENT, ClubAdminRole.MEMBER})
    public ContactInfoResponse updateContactInfo(String userReferenceId, UpdateContactInfoRequest updateContactInfoRequest) {

        Club club = clubRepository.findById(updateContactInfoRequest.clubId())
                .orElseThrow(ClubNotFoundException::new);

        ContactInfo contactInfo = club.getContactInfo().stream()
                .filter(c -> c.getId().equals(updateContactInfoRequest.contactInfoId()))
                .findFirst()
                .orElseThrow(ContactInfoNotFoundException::new);

        updateContactInfoRequest.updateToEntity(contactInfo);

        ContactInfo updateContactInfo = clubRepository.save(club).getContactInfo().stream()
                .filter(c -> c.getId().equals(updateContactInfoRequest.contactInfoId()))
                .findFirst()
                .orElseThrow(ContactInfoNotFoundException::new);

        return ContactInfoResponse.from(updateContactInfo);
    }

    @AdminAuthorizationCheck(role = {ClubAdminRole.PRESIDENT, ClubAdminRole.MEMBER})
    public UpdateClubActivityResponse updateClubActivity(String userReferenceId, UpdateClubActivityRequest updateClubActivityRequest) {
        Club club = clubRepository.findById(updateClubActivityRequest.clubId())
                .orElseThrow(ClubNotFoundException::new);

        Activity activity = club.getActivities().stream()
                .filter(a -> a.getId().equals(updateClubActivityRequest.activityId()))
                .findFirst()
                .orElseThrow(ActivityNotFoundException::new);

        updateClubActivityRequest.updateToEntity(activity);

        Activity updatedActivity = clubRepository.save(club).getActivities().stream()
                .filter(a -> a.getId().equals(updateClubActivityRequest.activityId()))
                .findFirst()
                .orElseThrow(ActivityNotFoundException::new);

        return UpdateClubActivityResponse.from(updatedActivity);
    }

    public boolean hasRoleForClub(String userReferenceId, Long clubId, ClubAdminRole requiredRole) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        ClubAdmin adminInfo = club.getAdmins().stream()
                .filter(admin -> admin.getUserReferenceId().equals(userReferenceId))
                .findFirst()
                .orElseThrow(ClubAdminNotFoundException::new);

        return adminInfo.getClubAdminRole().equals(requiredRole);
    }
}
