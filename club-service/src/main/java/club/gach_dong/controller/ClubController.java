package club.gach_dong.controller;

import club.gach_dong.api.ClubApiSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClubController implements ClubApiSpecification {
    @Override
    public String getUserInfo(String userReferenceId) {
        return "User Reference ID: " + userReferenceId;
    }
}
