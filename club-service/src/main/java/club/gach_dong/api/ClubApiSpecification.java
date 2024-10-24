package club.gach_dong.api;


import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.ClubActivityResponse;
import club.gach_dong.dto.response.ClubResponse;
import club.gach_dong.dto.response.ClubSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "동아리 API", description = "동아리 관련 API")
@RestController
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
    @GetMapping("/{clubId}")
    ClubResponse getClub(
            @Parameter(description = "동아리 ID", example = "ansier-enicsei-1233na-bndknar", required = true)
            @PathVariable String clubId
    );

    @Operation(
            summary = "동아리 생성",
            description = "동아리 정보를 입력받아 동아리를 생성합니다."
    )
    @PostMapping("/create")
    ResponseEntity<ClubResponse> createClub(
            @Valid
            @RequestBody
            CreateClubRequest createClubRequest
    );

    @Operation(
            summary = "동아리 활동 내역 조회",
            description = "동아리 활동 내역을 조회합니다."
    )
    @GetMapping("/{clubId}/activities")
    ArrayResponse<ClubActivityResponse> getClubActivities(
            @Parameter(description = "동아리 ID", example = "ansier-enicsei-1233na-bndknar", required = true)
            @PathVariable String clubId
    );

}
