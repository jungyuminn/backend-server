package club.gach_dong.club.service;

import club.gach_dong.club.dto.response.ClubResponse;
import club.gach_dong.club.dto.response.ClubSummaryResponse;

import java.util.List;

public interface ClubService {
    List<ClubSummaryResponse> getAllClubs();
    ClubResponse getClub(String clubId);
}
