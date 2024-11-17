package club.gach_dong.service;

import static club.gach_dong.exception.ClubException.*;

import club.gach_dong.domain.Activity;
import club.gach_dong.domain.Club;
import club.gach_dong.domain.ClubAdmin;
import club.gach_dong.domain.ContactInfo;
import club.gach_dong.domain.Recruitment;
import club.gach_dong.dto.response.AdminAuthorizedClubResponse;
import club.gach_dong.dto.response.ClubActivityResponse;
import club.gach_dong.dto.response.ClubContactInfoResponse;
import club.gach_dong.dto.response.ClubRecruitmentDetailResponse;
import club.gach_dong.dto.response.ClubRecruitmentResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import club.gach_dong.exception.ClubException.ClubNotFoundException;
import club.gach_dong.repository.ClubRepository;
import club.gach_dong.repository.RecruitmentRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubReadService {

    private final ClubRepository clubRepository;
    private final RecruitmentRepository recruitmentRepository;

    public List<ClubSummaryResponse> getAllClubs() {
        List<ClubSummaryResponse> clubList = new ArrayList<>();

        for (Club club : clubRepository.findAll()) {
            ClubSummaryResponse from = ClubSummaryResponse.of(club);
            clubList.add(from);
        }

        return clubList;
    }

    public ClubResponse getClub(Long clubIId) {
        return clubRepository.findById(clubIId)
                .map(ClubResponse::of)
                .orElseThrow(ClubNotFoundException::new);
    }

    public List<ClubActivityResponse> getClubActivities(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        List<Activity> activities = club.getActivities();

        return activities.stream()
                .map(ClubActivityResponse::of)
                .toList();
    }

    public List<ClubContactInfoResponse> getClubContactInfo(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        List<ContactInfo> contactInfos = club.getContactInfo();

        return contactInfos.stream()
                .map(ClubContactInfoResponse::of)
                .toList();
    }

    public List<ClubRecruitmentResponse> getClubsRecruitments() {
        List<Club> clubs = clubRepository.findAllWithRecruitments();

        return clubs.stream()
                .flatMap(club -> club.getRecruitment().stream()
                        .map(recruitment -> ClubRecruitmentResponse.from(club, recruitment)))
                .collect(Collectors.toList());
    }

    public List<ClubRecruitmentDetailResponse> getClubRecruitments(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        List<Recruitment> recruitments = club.getRecruitment();

        return recruitments.stream()
                .map(recruitment -> ClubRecruitmentDetailResponse.from(club, recruitment))
                .toList();
    }

    public ClubRecruitmentDetailResponse getClubRecruitment(Long clubId, Long recruitmentId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        Recruitment recruitment = club.getRecruitment().stream()
                .filter(r -> r.getId().equals(recruitmentId))
                .findFirst()
                .orElseThrow(RecruitmentNotFoundException::new);

        return ClubRecruitmentDetailResponse.from(club, recruitment);
    }

    public Boolean hasAuthority(String userReferenceId, Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        return club.getAdmins().stream()
                .anyMatch(admin -> admin.getUserReferenceId().equals(userReferenceId));
    }

    public Boolean isValidRecruitment(Long recruitmentId, LocalDateTime currentDateTime) {
        return recruitmentRepository.findById(recruitmentId)
                .map(recruitment -> recruitment.isRecruiting(currentDateTime))
                .orElse(false); // 모집 정보가 없으면 false 반환
    }

    public Boolean hasAuthorityByRecruitmentId(String userReferenceId, Long recruitmentId) {
        return recruitmentRepository.findById(recruitmentId)
                .map(Recruitment::getClub)
                .map(club -> club.getAdmins().stream()
                        .anyMatch(admin -> admin.getUserReferenceId().equals(userReferenceId)))
                .orElse(false);
    }

    public void authorizeAdmin(String userReferenceId, Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        ClubAdmin clubAdmin = ClubAdmin.createMember(userReferenceId, club);

        club.addAdminMember(clubAdmin);
        clubRepository.save(club);
    }

    public List<AdminAuthorizedClubResponse> getAuthorizedClubs(String userReferenceId) {
        List<Club> clubs = clubRepository.findAll();

        List<Club> clubByAdmin = clubs.stream()
                .filter(club -> club.getAdmins().stream()
                        .anyMatch(admin -> admin.getUserReferenceId().equals(userReferenceId)))
                .collect(Collectors.toList());

        if (clubByAdmin.isEmpty()) {
            return List.of();
        }

        return clubByAdmin.stream()
                .map(club -> club.getAdmins().stream()
                        .filter(a -> a.getUserReferenceId().equals(userReferenceId))
                        .findFirst()
                        .map(admin -> AdminAuthorizedClubResponse.from(club, admin))
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}