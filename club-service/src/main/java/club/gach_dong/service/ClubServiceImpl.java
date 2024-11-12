package club.gach_dong.service;

import club.gach_dong.domain.Activity;
import club.gach_dong.domain.Club;
import club.gach_dong.domain.ClubAdmin;
import club.gach_dong.domain.ContactInfo;
import club.gach_dong.domain.Recruitment;
import club.gach_dong.dto.request.CreateClubActivityRequest;
import club.gach_dong.dto.request.CreateClubContactInfoRequest;
import club.gach_dong.dto.request.CreateClubRecruitmentRequest;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.AdminAuthorizedClubResponse;
import club.gach_dong.dto.response.ClubActivityResponse;
import club.gach_dong.dto.response.ClubContactInfoResponse;
import club.gach_dong.dto.response.ClubRecruitmentDetailResponse;
import club.gach_dong.dto.response.ClubRecruitmentResponse;
import club.gach_dong.dto.response.CreateClubActivityResponse;
import club.gach_dong.dto.response.CreateClubContactInfoResponse;
import club.gach_dong.dto.response.CreateClubRecruitmentResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import club.gach_dong.exception.ClubException;
import club.gach_dong.exception.ClubException.ClubNotFoundException;
import club.gach_dong.repository.ClubRepository;
import club.gach_dong.repository.RecruitmentRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Override
    public List<ClubSummaryResponse> getAllClubs() {
        List<ClubSummaryResponse> clubList = new ArrayList<>();

        for (Club club : clubRepository.findAll()) {
            ClubSummaryResponse from = ClubSummaryResponse.from(club);
            clubList.add(from);
        }

        return clubList;
    }

    @Override
    public ClubResponse getClub(Long clubIId) {
        return clubRepository.findById(clubIId)
                .map(ClubResponse::from)
                .orElseThrow(ClubNotFoundException::new);
    }

    @Override
    public List<ClubActivityResponse> getClubActivities(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        List<Activity> activities = club.getActivities();

        return activities.stream()
                .map(ClubActivityResponse::from)
                .toList();
    }

    @Override
    public List<ClubContactInfoResponse> getClubContactInfo(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        List<ContactInfo> contactInfos = club.getContactInfo();

        return contactInfos.stream()
                .map(ClubContactInfoResponse::from)
                .toList();
    }

    @Override
    public List<ClubRecruitmentResponse> getClubsRecruitments() {
        List<Club> clubs = clubRepository.findAllWithRecruitments();

        return clubs.stream()
                .flatMap(club -> club.getRecruitment().stream()
                        .map(recruitment -> ClubRecruitmentResponse.of(club, recruitment)))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClubRecruitmentDetailResponse> getClubRecruitments(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        List<Recruitment> recruitments = club.getRecruitment();

        return recruitments.stream()
                .map(recruitment -> ClubRecruitmentDetailResponse.of(club, recruitment))
                .toList();
    }

    @Override
    public ClubRecruitmentDetailResponse getClubRecruitment(Long clubId, Long recruitmentId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        Recruitment recruitment = club.getRecruitment().stream()
                .filter(r -> r.getId().equals(recruitmentId))
                .findFirst()
                .orElseThrow(ClubException.RecruitmentNotFoundException::new);

        return ClubRecruitmentDetailResponse.of(club, recruitment);
    }

    // admin
    @Override
    @Transactional
    public ClubResponse createClub(String userReferenceId, CreateClubRequest createClubRequest) {

        Club club = Club.of(
                createClubRequest.name(),
                createClubRequest.category(),
                createClubRequest.shortDescription(),
                createClubRequest.introduction(),
                createClubRequest.clubImageUrl(),
                userReferenceId,
                createClubRequest.establishedAt()
        );
        Club savedClub = clubRepository.save(club);
        return ClubResponse.from(savedClub);
    }

    @Override
    @Transactional
    public CreateClubActivityResponse createClubActivity(
            String userReferenceId,
            CreateClubActivityRequest createClubActivityRequest
    ) {

        Club club = clubRepository.findById(createClubActivityRequest.clubId())
                .orElseThrow(ClubNotFoundException::new);

        Activity activity = Activity.of(
                createClubActivityRequest.title(),
                createClubActivityRequest.date(),
                createClubActivityRequest.description(),
                club
        );

        club.addActivity(activity);

        clubRepository.save(club);

        return CreateClubActivityResponse.of(
                club.getId(),
                activity.getId()
        );
    }

    @Override
    @Transactional
    public CreateClubContactInfoResponse createClubContactInfo(
            String userReferenceId,
            CreateClubContactInfoRequest createClubContactInfoRequest
    ) {
        Club club = clubRepository.findById(createClubContactInfoRequest.clubId())
                .orElseThrow(ClubNotFoundException::new);

        ContactInfo contactInfo = ContactInfo.of(
                createClubContactInfoRequest.type(),
                createClubContactInfoRequest.contact(),
                club
        );

        club.addContactInfo(contactInfo);

        clubRepository.save(club);

        return CreateClubContactInfoResponse.of(
                club.getId(),
                contactInfo.getId()
        );
    }

    @Override
    @Transactional
    public CreateClubRecruitmentResponse createClubRecruitment(
            String userReferenceId,
            CreateClubRecruitmentRequest createClubRecruitmentRequest
    ) {
        Club club = clubRepository.findById(createClubRecruitmentRequest.clubId())
                .orElseThrow(ClubNotFoundException::new);

        Recruitment recruitment = Recruitment.of(
                createClubRecruitmentRequest.title(),
                createClubRecruitmentRequest.content(),
                createClubRecruitmentRequest.recruitmentCount(),
                createClubRecruitmentRequest.startDate(),
                createClubRecruitmentRequest.endDate(),
                createClubRecruitmentRequest.processData(),
                club
        );

        club.addRecruitment(recruitment);

        clubRepository.save(club);

        return new CreateClubRecruitmentResponse();
    }

    @Override
    public List<AdminAuthorizedClubResponse> getAuthorizedClubs(String userReferenceId) {
        List<Club> clubs = clubRepository.findAll();

        List<Club> clubByAdmin = clubs.stream()
                .filter(club -> club.getAdmins().stream()
                        .anyMatch(admin -> admin.getUserReferenceId().equals(userReferenceId)))
                .collect(Collectors.toList());

        if (clubByAdmin.isEmpty()) {
            throw new ClubNotFoundException();
        }

        return clubByAdmin.stream()
                .map(club -> {
                    ClubAdmin admin = club.getAdmins().stream()
                            .filter(a -> a.getUserReferenceId().equals(userReferenceId))
                            .findFirst()
                            .orElseThrow(ClubNotFoundException::new);
                    return AdminAuthorizedClubResponse.from(club, admin);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Boolean hasAuthority(String userReferenceId, Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        return club.getAdmins().stream()
                .anyMatch(admin -> admin.getUserReferenceId().equals(userReferenceId));
    }

    @Override
    public Boolean isValidRecruitment(Long recruitmentId, LocalDateTime currentDateTime) {
        return recruitmentRepository.findById(recruitmentId)
                .map(recruitment -> recruitment.isRecruiting(currentDateTime))
                .orElse(false); // 모집 정보가 없으면 false 반환
    }
}