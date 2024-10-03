package club.gach_dong.club.controller;

import club.gach_dong.club.api.ClubApiSpecification;
import club.gach_dong.club.dto.response.ClubResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClubController implements ClubApiSpecification {

    @Override
    public ClubResponse getClub(String clubId) {
        return ClubResponse.of(clubId);
    }
}
