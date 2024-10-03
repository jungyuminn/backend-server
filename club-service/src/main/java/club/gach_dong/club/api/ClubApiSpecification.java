package club.gach_dong.club.api;


import club.gach_dong.club.dto.response.ClubResponse;
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
@Validated
@RequestMapping("v1/club")
public interface ClubApiSpecification {

    @Operation(
            summary = "동아리 조회",
            description = "동아리 ID를 이용하여 동아리 정보를 조회합니다."
    )
    @GetMapping("/{clubId}")
    ClubResponse getClub(
            @Parameter(description = "동아리 ID", example = "01HGW2N7EHJVJ4CJ999RRS2E97", required = true)
            @PathVariable
            String clubId
    );
}
