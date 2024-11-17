package club.gach_dong.service;

import static club.gach_dong.domain.ClubCategory.ACADEMIC;
import static club.gach_dong.domain.ClubCategory.SPORTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

import club.gach_dong.domain.Club;
import club.gach_dong.domain.ClubCategory;
import club.gach_dong.domain.ContactInfo;
import club.gach_dong.domain.Recruitment;
import club.gach_dong.dto.request.CreateClubActivityRequest;
import club.gach_dong.dto.request.CreateClubContactInfoRequest;
import club.gach_dong.dto.request.CreateClubRecruitmentRequest;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import club.gach_dong.dto.response.CreateClubActivityResponse;
import club.gach_dong.dto.response.CreateClubContactInfoResponse;
import club.gach_dong.dto.response.CreateClubRecruitmentResponse;
import club.gach_dong.repository.ClubRepository;
import io.swagger.v3.core.util.Json;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ClubServiceImplTest {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubService clubService;

    @DisplayName("모집공고를 생성하면, 동아리 ID, 동아리 지원서 양식 ID 및 모집공고 ID를 반환한다.")
    @Test
    void createClubRecruitment() {
        // given
        Club club = createClub("동아리 1", ACADEMIC, "회장 UUID");
        clubRepository.save(club);

        // when
        JSONObject data = new JSONObject();
        CreateClubRecruitmentRequest createClubRecruitmentRequest = new CreateClubRecruitmentRequest(club.getId(), "모집공고 제목","모집공고 내용", 10L,  1L, LocalDateTime.now(), LocalDateTime.now(), data);
        CreateClubRecruitmentResponse createClubRecruitmentResponse = clubService.createClubRecruitment("회장 UUID", createClubRecruitmentRequest);

        // then
        assertThat(createClubRecruitmentResponse.clubId()).isEqualTo(club.getId());
        assertThat(createClubRecruitmentResponse.applicationFormId()).isEqualTo(1L);
        assertThat(createClubRecruitmentResponse.clubRecruitmentId()).isNotNull();

    }

    @DisplayName("활동 내역 생성하면, 동아리 ID와 모집공고 ID를 반환한다.")
    @Test
    void createClubActivity() {
        // given
        Club club = createClub("동아리 1", ACADEMIC, "회장 UUID");
        clubRepository.save(club);

        // when
        CreateClubActivityRequest createClubActivityRequest = new CreateClubActivityRequest(club.getId(), "활동 내역 제목", "활동 내역 내용", LocalDate.now());
        CreateClubActivityResponse createClubActivityResponse = clubService.createClubActivity("회장 UUID", createClubActivityRequest);

        // then
        assertThat(createClubActivityResponse.clubId()).isEqualTo(club.getId());
        assertThat(createClubActivityResponse.activityId()).isNotNull();

    }

    @DisplayName("동아리 연락처를 생성하면, 동아리 ID와 모집공고 ID를 반환한다.")
    @Test
    void createClubContactInfo() {
        // given
        Club club = createClub("동아리 1", ACADEMIC, "회장 UUID");
        clubRepository.save(club);

        // when
        CreateClubContactInfoRequest createClubContactInfoRequest = new CreateClubContactInfoRequest(club.getId(), "동아리 연락처 제목", "010-1234-5678");
        CreateClubContactInfoResponse createClubContactInfoResponse = clubService.createClubContactInfo("회장 UUID", createClubContactInfoRequest);

        // then
        assertThat(createClubContactInfoResponse.clubId()).isEqualTo(club.getId());
        assertThat(createClubContactInfoResponse.ContactId()).isNotNull();
    }

    @DisplayName("모든 동아리를 조회한다.")
    @Test
    void getAllClubs() {
        // given
        Club club1 = createClub("동아리 A", ACADEMIC, "회장 UUID 1234");
        Club club2 = createClub("동아리 B", SPORTS, "회장 UUID 1424");
        Club club3 = createClub("동아리 C", ACADEMIC, "회장 UUID 1265");

        clubRepository.saveAll(List.of(club1, club2, club3));

        // when
        List<ClubSummaryResponse> clubs = clubService.getAllClubs();

        // then
        assertThat(clubs).hasSize(3)
                .extracting("clubName", "category")
                .containsExactlyInAnyOrder(
                        tuple("동아리 A", ACADEMIC),
                        tuple("동아리 B", SPORTS),
                        tuple("동아리 C", ACADEMIC)
                );
    }

    @DisplayName("clubId를 통해 동아리를 조회한다.")
    @Test
    void getClub() {
        // given
        Club club1 = createClub("동아리 1", ACADEMIC, "회장 UUID 1234");
        Club club2 = createClub("동아리 2", SPORTS, "회장 UUID 1424");
        Club club3 = createClub("동아리 3", ACADEMIC, "회장 UUID 1265");

        clubRepository.saveAll(List.of(club1, club2, club3));

        // when
        ClubResponse club = clubService.getClub(club1.getId());

        // then
        assertThat(club).extracting("clubId", "clubName").containsExactly(club1.getId(), "동아리 1");
    }

    private Club createClub(String clubName, ClubCategory category, String userReferenceId) {
        return Club.builder()
                .name(clubName)
                .category(category)
                .shortDescription("동아리 한줄 소개")
                .introduction("동아리 설명")
                .clubImageUrl("이미지 URL")
                .userReferenceId(userReferenceId)
                .establishedAt(LocalDateTime.now())
                .build();
    }
}