package club.gach_dong.service;

import club.gach_dong.dto.request.CreateClubActivityRequest;
import club.gach_dong.dto.request.CreateClubContactInfoRequest;
import club.gach_dong.dto.request.CreateClubRecruitmentRequest;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.ClubActivityResponse;
import club.gach_dong.dto.response.ClubContactInfoResponse;
import club.gach_dong.dto.response.ClubRecruitmentDetailResponse;
import club.gach_dong.dto.response.ClubRecruitmentResponse;
import club.gach_dong.dto.response.CreateClubActivityResponse;
import club.gach_dong.dto.response.CreateClubContactInfoResponse;
import club.gach_dong.dto.response.CreateClubRecruitmentResponse;
import club.gach_dong.dto.response.CreateClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;

import java.util.List;

public interface ClubService {
    List<ClubSummaryResponse> getAllClubs();
    CreateClubResponse getClub(Long clubId);
    List<ClubActivityResponse> getClubActivities(Long clubId);
    List<ClubContactInfoResponse> getClubContactInfo(Long clubId);
    List<ClubRecruitmentResponse> getClubsRecruitments();
    List<ClubRecruitmentDetailResponse> getClubRecruitments(Long clubId);
    ClubRecruitmentDetailResponse getClubRecruitment(Long clubId, Long recruitmentId);

    //admin
    CreateClubResponse createClub(CreateClubRequest createClubRequest);
    CreateClubActivityResponse createClubActivity(String userReferenceId, CreateClubActivityRequest createClubActivityRequest);
    CreateClubContactInfoResponse createClubContactInfo(String userReferenceId, CreateClubContactInfoRequest createClubContactInfoRequest);
    CreateClubRecruitmentResponse createClubRecruitment(String userReferenceId, CreateClubRecruitmentRequest createClubRecruitmentRequest);
}
