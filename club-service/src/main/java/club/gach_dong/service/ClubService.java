package club.gach_dong.service;

import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.ClubActivityResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;

import java.util.List;

public interface ClubService {
    List<ClubSummaryResponse> getAllClubs();
    ClubResponse getClub(String clubId);
    ClubResponse createClub(CreateClubRequest createClubRequest);
    List<ClubActivityResponse> getClubActivities(String clubId);
}
