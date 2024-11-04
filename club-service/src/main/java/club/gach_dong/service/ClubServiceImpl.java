package club.gach_dong.service;

import club.gach_dong.domain.Activity;
import club.gach_dong.domain.Club;
import club.gach_dong.domain.ContactInfo;
import club.gach_dong.domain.Recruitment;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.ClubActivityResponse;
import club.gach_dong.dto.response.ClubContactInfoResponse;
import club.gach_dong.dto.response.ClubRecruitmentDetailResponse;
import club.gach_dong.dto.response.ClubRecruitmentResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import club.gach_dong.repository.ClubRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

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
    public ClubResponse getClub(Long clubIId) {
        return clubRepository.findById(clubIId)
                .map(ClubResponse::from)
                .orElseThrow(() -> new NotFoundException("Club not found"));
    }

    @Override
    public ClubResponse createClub(CreateClubRequest createClubRequest) {
        Club club = Club.of(
                createClubRequest.name(),
                createClubRequest.category(),
                createClubRequest.shortDescription(),
                createClubRequest.introduction(),
                createClubRequest.clubImageUrl(),
                createClubRequest.establishedAt()
        );
        Club savedClub = clubRepository.save(club);
        return ClubResponse.from(savedClub);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubActivityResponse> getClubActivities(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NotFoundException("Club not found"));

        List<Activity> activities = club.getActivities();

        return activities.stream()
                .map(ClubActivityResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubContactInfoResponse> getClubContactInfo(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NotFoundException("Club not found"));

        List<ContactInfo> contactInfos = club.getContactInfo();

        return contactInfos.stream()
                .map(ClubContactInfoResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubRecruitmentResponse> getClubRecruitmentList() {
        List<Club> clubs = clubRepository.findAllWithRecruitments();

        return clubs.stream()
                .flatMap(club -> club.getRecruitment().stream()
                        .map(recuitment -> ClubRecruitmentResponse.of(club, recuitment)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubRecruitmentDetailResponse> getClubRecruitment(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NotFoundException("Club not found"));

        List<Recruitment> recruitments = club.getRecruitment();

        return recruitments.stream()
                .map(recruitment -> ClubRecruitmentDetailResponse.of(club, recruitment))
                .toList();
    }
}