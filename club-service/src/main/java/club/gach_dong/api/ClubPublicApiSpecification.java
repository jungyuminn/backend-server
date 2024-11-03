package club.gach_dong.api;

import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.ClubActivityResponse;
import club.gach_dong.dto.response.ClubContactInfoResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Public 동아리 API", description = "Public한 동아리 관련 API")
@RestController
@RequestMapping("/public/api/v1")
@Validated
public interface ClubPublicApiSpecification {

    @Operation(
            summary = "동아리 목록 조회",
            description = "모든 동아리 정보를 조회합니다."
    )
    @GetMapping("/")
    ArrayResponse<ClubSummaryResponse> getClubs();

    @Operation(
            summary = "동아리 조회",
            description = "동아리 이름을 이용하여 동아리 정보를 조회합니다."
    )
    @GetMapping("/{clubId}")
    ClubResponse getClub(
            @Parameter(description = "동아리 ID", example = "1", required = true)
            @PathVariable Long clubId
    );

    @Operation(
            summary = "동아리 활동 내역 조회",
            description = "동아리 활동 내역을 조회합니다."
    )
    @GetMapping("/{clubId}/activities")
    ArrayResponse<ClubActivityResponse> getClubActivities(
            @Parameter(description = "동아리 ID", example = "1", required = true)
            @PathVariable Long clubId
    );

    @Operation(
            summary = "동아리 연락처 정보 조회",
            description = "동아리 연락처 정보를 조회합니다."
    )
    @GetMapping("/{clubId}/contact-info")
    ArrayResponse<ClubContactInfoResponse> getClubContactInfo(
            @Parameter(description = "동아리 ID", example = "1", required = true)
            @PathVariable Long clubId
    );

    @GetMapping("/test")
    List<String> getClubTest();
}
