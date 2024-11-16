package club.gach_dong.service;

import static club.gach_dong.domain.ClubCategory.ACADEMIC;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
class ClubServiceImplTest {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubService clubService;

    @DisplayName("모집공고를 생성하면, 동아리 ID와 모집공고 ID를 반환한다.")
    @Test
    void createClubRecruitment() {
        // given
        Club club = Club.of("동아리 이름", ACADEMIC, "동아리 소개", "동아리 소개", "이미지 URL", "회장 UUID", LocalDateTime.now());
        clubRepository.save(club);

        // when
        JSONObject data = new JSONObject();
        CreateClubRecruitmentRequest createClubRecruitmentRequest = new CreateClubRecruitmentRequest(1L, "모집공고 제목","모집공고 내용", 10L,  LocalDateTime.now(), LocalDateTime.now(), data);
        CreateClubRecruitmentResponse createClubRecruitmentResponse = clubService.createClubRecruitment("회장 UUID", createClubRecruitmentRequest);

        // then
        assertThat(createClubRecruitmentResponse).extracting("clubId", "clubRecruitmentId").containsExactly(1L, 1L);

    }

    @DisplayName("활동 내역 생성하면, 동아리 ID와 모집공고 ID를 반환한다.")
    @Test
    void createClubActivity() {
        // given
        Club club = Club.of("동아리 이름", ACADEMIC, "동아리 소개", "동아리 소개", "이미지 URL", "회장 UUID", LocalDateTime.now());
        clubRepository.save(club);

        // when
        CreateClubActivityRequest createClubActivityRequest = new CreateClubActivityRequest(1L, "활동 내역 제목", "활동 내역 내용", LocalDate.now());
        CreateClubActivityResponse createClubActivityResponse = clubService.createClubActivity("회장 UUID", createClubActivityRequest);

        // then
        assertThat(createClubActivityResponse).extracting("clubId", "activityId").containsExactly(1L, 1L);

    }

    @DisplayName("동아리 연락처를 생성하면, 동아리 ID와 모집공고 ID를 반환한다.")
    @Test
    void createClubContactInfo() {
        // given
        Club club = Club.of("동아리 이름", ACADEMIC, "동아리 소개", "동아리 소개", "이미지 URL", "회장 UUID", LocalDateTime.now());
        clubRepository.save(club);

        // when
        CreateClubContactInfoRequest createClubContactInfoRequest = new CreateClubContactInfoRequest(1L, "동아리 연락처 제목", "010-1234-5678");
        CreateClubContactInfoResponse createClubContactInfoResponse = clubService.createClubContactInfo("회장 UUID", createClubContactInfoRequest);

        // then
        assertThat(createClubContactInfoResponse).extracting("clubId", "ContactId").containsExactly(1L, 1L);
    }

    @DisplayName("모든 동아리를 조회한다.")
    @Test
    void getAllClubs() {
        // given
        Club club1 = Club.of("동아리 이름1", ACADEMIC, "동아리 소개", "동아리 소개", "이미지 URL", "회장 UUID", LocalDateTime.now());
        Club club2 = Club.of("동아리 이름2", ACADEMIC, "동아리 소개", "동아리 소개", "이미지 URL", "회장 UUID", LocalDateTime.now());
        Club club3 = Club.of("동아리 이름3", ACADEMIC, "동아리 소개", "동아리 소개", "이미지 URL", "회장 UUID", LocalDateTime.now());

        clubRepository.saveAll(List.of(club1, club2, club3));

        // when
        List<ClubSummaryResponse> clubs = clubService.getAllClubs();

        // then
        assertThat(clubs).hasSize(3)
                .extracting("clubId", "clubName")
                .containsExactlyInAnyOrder(
                        tuple(1L,"동아리 이름1"),
                        tuple(2L,"동아리 이름2"),
                        tuple(3L,"동아리 이름3")
                );
    }

    @DisplayName("clubId를 통해 동아리를 조회한다.")
    @Test
    void getClub() {
        // given
        Club club1 = Club.of("동아리 이름1", ACADEMIC, "동아리 소개", "동아리 소개", "이미지 URL", "회장 UUID", LocalDateTime.now());
        Club club2 = Club.of("동아리 이름2", ACADEMIC, "동아리 소개", "동아리 소개", "이미지 URL", "회장 UUID", LocalDateTime.now());
        Club club3 = Club.of("동아리 이름3", ACADEMIC, "동아리 소개", "동아리 소개", "이미지 URL", "회장 UUID", LocalDateTime.now());

        clubRepository.saveAll(List.of(club1, club2, club3));

        // when
        ClubResponse club = clubService.getClub(1L);

        // then
        assertThat(club).extracting("clubId", "clubName").containsExactly(1L, "동아리 이름1");

    }
}