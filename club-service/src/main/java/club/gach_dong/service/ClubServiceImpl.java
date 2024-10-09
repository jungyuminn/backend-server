package club.gach_dong.service;

import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import club.gach_dong.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    @Override
    public List<ClubSummaryResponse> getAllClubs() {
        return clubRepository.findAll().stream()
                .map(ClubSummaryResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public ClubResponse getClub(String name) {
        return clubRepository.findByName(name)
                .map(ClubResponse::from)
                .orElseThrow(() -> new RuntimeException("Club not found"));
    }
}