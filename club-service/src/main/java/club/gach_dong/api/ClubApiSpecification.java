package club.gach_dong.api;


import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "동아리 API", description = "동아리 관련 API")
@RestController
@RequestMapping('/api/v1/**')
@Validated
public interface ClubApiSpecification {

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
    @GetMapping("/{clubName}")
    ClubResponse getClub(
            @Parameter(description = "동아리 이름", example = "GDSC", required = true)
            @PathVariable
            String clubName
    );
}
