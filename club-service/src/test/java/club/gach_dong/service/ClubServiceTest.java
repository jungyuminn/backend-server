package club.gach_dong.service;

import static org.assertj.core.api.Assertions.assertThat;

import club.gach_dong.domain.Club;
import club.gach_dong.domain.ClubCategory;
import club.gach_dong.dto.request.UpdateClubRequest;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.repository.ClubRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ClubServiceTest {

    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubRepository clubRepository;


    @DisplayName("동아리 정보 업데이트가 정상적으로 이루어진다.")
    @Test
    void updateClubInfo() {
        // given
        Club club = Club.builder()
                .userReferenceId("userReferenceId")
                .name("name")
                .shortDescription("shortDescription")
                .introduction("introduction")
                .category(ClubCategory.SPORTS)
                .clubImageUrl("clubImageUrl")
                .establishedAt(LocalDateTime.now())
                .build();

        clubRepository.save(club);

        // when
        UpdateClubRequest updateClubRequest = new UpdateClubRequest(1L, "newName", ClubCategory.EXHIBITION, "newShortDescription", "newIntroduction", "newClubImageUrl", LocalDateTime.now());
        ClubResponse updateClubInfo = clubService.updateClubInfo("userReferenceId", updateClubRequest);

        // then
        Club assertClub = clubRepository.findById(1L).get();

        assertThat(assertClub).extracting("id", "name", "category", "shortDescription", "introduction", "clubImageUrl")
                .containsExactly(1L, "newName", ClubCategory.EXHIBITION, "newShortDescription", "newIntroduction", "newClubImageUrl");

        assertThat(updateClubInfo).extracting("clubId", "clubName", "category", "shortDescription", "introduction", "clubImageUrl")
                .containsExactly(1L, "newName", ClubCategory.EXHIBITION, "newShortDescription", "newIntroduction", "newClubImageUrl");
    }
}