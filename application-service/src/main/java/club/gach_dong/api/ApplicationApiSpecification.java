package club.gach_dong.api;

import club.gach_dong.dto.request.ApplicationRequestDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO;
import club.gach_dong.response.ResForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "지원 API", description = "동아리 지원 관련 API")
@RestController
@Validated
@RequestMapping("v1/application")
public interface ApplicationApiSpecification {

    @Operation(summary = "관리자용 지원서 양식 조회 API", description = "지원서 양식 ID를 이용해 양식을 조회합니다.")
    @GetMapping("/admin/form/{formId}")
    ResForm<ApplicationResponseDTO.ToGetFormInfoAdminDTO> getFormInfoAdmin(@PathVariable("formId") Long formId, HttpServletRequest httpServletRequest);

    @Operation(summary = "사용자용 지원서 양식 조회 API", description = "지원서 양식 ID를 이용해 양식을 조회합니다.")
    @GetMapping("/form/{formId}")
    ResForm<ApplicationResponseDTO.ToGetFormInfoUserDTO> getFormInfoUser(@PathVariable("formId") Long formId, HttpServletRequest httpServletRequest);

    @Operation(summary = "사용자 지원 내역 목록 조회 API", description = "지원 내역 목록을 조회합니다.")
    @GetMapping("/application/list")
    ResForm<ApplicationResponseDTO.ToGetApplicationHistoryListDTO> getaApplicationHistory(HttpServletRequest httpServletRequest);

    @Operation(summary = "동아리 지원서 양식 생성 요청 API", description = "동아리 지원서 양식을 생성합니다.")
    @PostMapping("/application/admin/form/create")
    ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> createApplicationForm(@Valid @RequestBody ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO, HttpServletRequest httpServletRequest);

    @Operation(summary = "동아리 지원 API", description = "동아리에 지원합니다.")
    @PostMapping(value = "/{apply_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResForm<ApplicationResponseDTO.ToCreateApplicationDTO> createApplication(
            @PathVariable("apply_id") Long applyId,
            @RequestPart(value = "files", required = false) @Parameter(description = "업로드할 문서 리스트") List<MultipartFile> files,
            @RequestPart(value = "toApplyClub") @Parameter(description = "동아리 지원에 필요한 요청 데이터") @Valid ApplicationRequestDTO.ToApplyClubDTO toApplyClub,
            HttpServletRequest httpServletRequest
    );

    @Operation(summary = "동아리 지원서 양식 수정 요청 API", description = "동아리 지원서 양식을 수정합니다.")
    @PutMapping("/application/admin/form/{form_id}")
    ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> changeApplicationForm(@PathVariable("form_id") Long formId, @Valid @RequestBody ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO, HttpServletRequest httpServletRequest);

    @Operation(summary = "동아리 지원 수정 API", description = "동아리에 지원을 수정합니다.")
    @PutMapping(value = "/application/{apply_id} ", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResForm<ApplicationResponseDTO.ToCreateApplicationDTO> changeApplication(
            @PathVariable("apply_id") Long applyId,
            @RequestPart(value = "certificateDocs", required = true) @Parameter(description = "업로드할 문서 리스트") List<MultipartFile> certificateDocs,
            @RequestPart(value = "toApplyClub", required = true) @Parameter(description = "동아리 지원에 필요한 요청 데이터") @Valid ApplicationRequestDTO.ToApplyClubDTO toApplyClub,
            HttpServletRequest httpServletRequest);

    @Operation(summary = "지원 양식 삭제 API", description = "지원서 양식 ID를 이용해 양식을 삭제합니다.")
    @DeleteMapping("/admin/form/{formId}")
    ResForm<?> deleteApplicationForm(@PathVariable("formId") Long formId, HttpServletRequest httpServletRequest);

    @Operation(summary = "사용자 지원 취소 API", description = "지원 ID를 이용해 지원을 취소합니다.")
    @DeleteMapping("/apply/{applyId}")
    ResForm<?> deleteApplication(@PathVariable("applyId") Long applyId, HttpServletRequest httpServletRequest);

}

