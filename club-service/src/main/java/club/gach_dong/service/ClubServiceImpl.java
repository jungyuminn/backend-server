package club.gach_dong.service;

import club.gach_dong.domain.Activity;
import club.gach_dong.domain.Club;
import club.gach_dong.domain.ContactInfo;
import club.gach_dong.domain.Recruitment;
import club.gach_dong.dto.request.CreateClubActivityRequest;
import club.gach_dong.dto.request.CreateClubContactInfoRequest;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.ClubActivityResponse;
import club.gach_dong.dto.response.ClubContactInfoResponse;
import club.gach_dong.dto.response.ClubRecruitmentDetailResponse;
import club.gach_dong.dto.response.ClubRecruitmentResponse;
import club.gach_dong.dto.response.CreateClubActivityResponse;
import club.gach_dong.dto.response.CreateClubContactInfoResponse;
import club.gach_dong.dto.response.CreateClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import club.gach_dong.exception.ClubException;
import club.gach_dong.repository.ClubRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClubSummaryResponse> getAllClubs() {
        List<ClubSummaryResponse> clubList = new ArrayList<>();

        for (Club club : clubRepository.findAll()) {
            ClubSummaryResponse from = ClubSummaryResponse.from(club);
            clubList.add(from);
        }

        return clubList;
    }

    @Override
    @Transactional(readOnly = true)
    public CreateClubResponse getClub(Long clubIId) {
        return clubRepository.findById(clubIId)
                .map(CreateClubResponse::from)
                .orElseThrow(ClubException.ClubNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubActivityResponse> getClubActivities(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubException.ClubNotFoundException::new);

        List<Activity> activities = club.getActivities();

        return activities.stream()
                .map(ClubActivityResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubContactInfoResponse> getClubContactInfo(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubException.ClubNotFoundException::new);

        List<ContactInfo> contactInfos = club.getContactInfo();

        return contactInfos.stream()
                .map(ClubContactInfoResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubRecruitmentResponse> getClubsRecruitments() {
        List<Club> clubs = clubRepository.findAllWithRecruitments();

        return clubs.stream()
                .flatMap(club -> club.getRecruitment().stream()
                        .map(recruitment -> ClubRecruitmentResponse.of(club, recruitment)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubRecruitmentDetailResponse> getClubRecruitments(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubException.ClubNotFoundException::new);

        List<Recruitment> recruitments = club.getRecruitment();

        return recruitments.stream()
                .map(recruitment -> ClubRecruitmentDetailResponse.of(club, recruitment))
                .toList();
    }

    @Override
    public ClubRecruitmentDetailResponse getClubRecruitment(Long clubId, Long recruitmentId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubException.ClubNotFoundException::new);

        Recruitment recruitment = club.getRecruitment().stream()
                .filter(r -> r.getId().equals(recruitmentId))
                .findFirst()
                .orElseThrow(ClubException.RecruitmentNotFoundException::new);

        return ClubRecruitmentDetailResponse.of(club, recruitment);
    }

    // admin
    @Override
    @Transactional
    public CreateClubResponse createClub(CreateClubRequest createClubRequest) {
        Club club = Club.of(
                createClubRequest.name(),
                createClubRequest.category(),
                createClubRequest.shortDescription(),
                createClubRequest.introduction(),
                createClubRequest.clubImageUrl(),
                createClubRequest.establishedAt()
        );
        Club savedClub = clubRepository.save(club);
        return CreateClubResponse.from(savedClub);
    }

    @Override
    @Transactional
    public CreateClubActivityResponse createClubActivity(
            String userReferenceId,
            CreateClubActivityRequest createClubActivityRequest
    ) {

        Club club = clubRepository.findById(createClubActivityRequest.clubId())
                .orElseThrow(ClubException.ClubNotFoundException::new);

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
                .orElseThrow(ClubException.ClubNotFoundException::new);

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

//    @Override
//    public CreateClubRecruitmentResponse createClubRecruitment(
//            String userReferenceId,
//            CreateClubRecruitmentRequest createClubRecruitmentRequest
//    ) {
//        Club club = clubRepository.findById(createClubContactInfoRequest.clubId())
//                .orElseThrow(() -> new NotFoundException("Club not found"));
//
//        ContactInfo contactInfo = ContactInfo.of(
//                createClubContactInfoRequest.type(),
//                createClubContactInfoRequest.contact(),
//                club
//        );
//
//        club.addContactInfo(contactInfo);
//
//        clubRepository.save(club);
//
//        return CreateClubContactInfoResponse.of(
//                club.getId(),
//                contactInfo.getId()
//        );
//    }
}