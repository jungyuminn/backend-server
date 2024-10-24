package club.gach_dong.service;

import club.gach_dong.domain.Club;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import club.gach_dong.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

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
    public ClubResponse getClub(String id) {
        return clubRepository.findById(id)
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
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                createClubRequest.establishedAt()
        );
        Club savedClub = clubRepository.save(club);
        return ClubResponse.from(savedClub);
    }
}