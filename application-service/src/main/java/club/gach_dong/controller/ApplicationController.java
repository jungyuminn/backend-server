package club.gach_dong.controller;

import club.gach_dong.api.ApplicationApiSpecification;
import club.gach_dong.dto.request.ApplicationRequestDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO;
import club.gach_dong.response.ResForm;
import club.gach_dong.response.status.InSuccess;
import club.gach_dong.service.ApplicationService;
import club.gach_dong.service.AuthorizationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ApplicationController implements ApplicationApiSpecification {

    public final ApplicationService applicationService;
    public final AuthorizationService authorizationService;

    @Override
    public ResForm<ApplicationResponseDTO.ToGetFormInfoUserDTO> getFormInfoUser(Long formId,
                                                                                String userReferenceId) {

        ApplicationResponseDTO.ToGetFormInfoUserDTO toGetFormInfoUserDTO = applicationService.getFormInfoUser(formId,
                userReferenceId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_GET_USER_INFO, toGetFormInfoUserDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToGetApplicationHistoryListDTO> getApplicationHistory(
            String userReferenceId) {

        ApplicationResponseDTO.ToGetApplicationHistoryListDTO toGetApplicationHistoryListDTO = applicationService.getApplicationHistoryList(
                userReferenceId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_GET_USER_INFO, toGetApplicationHistoryListDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationDTO> createApplication(Long applyId,
                                                                                    List<MultipartFile> files,
                                                                                    ApplicationRequestDTO.ToApplyClubDTO toApplyClub,
                                                                                    String userReferenceId) {

        ApplicationResponseDTO.ToCreateApplicationDTO toCreateApplicationDTO = applicationService.createApplication(
                applyId, files, toApplyClub, userReferenceId);
        return ResForm.onSuccess(InSuccess.APPLICATION_SUCCESS, toCreateApplicationDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationDTO> changeApplication(Long applyId,
                                                                                    List<MultipartFile> certificateDocs,
                                                                                    ApplicationRequestDTO.ToApplyClubDTO toApplyClub,
                                                                                    String userReferenceId) {

        ApplicationResponseDTO.ToCreateApplicationDTO toChangeApplicationDTO = applicationService.changeApplication(
                applyId, certificateDocs, toApplyClub, userReferenceId);
        return ResForm.onSuccess(InSuccess.APPLICATION_CHANGED, toChangeApplicationDTO);
    }

    @Override
    public ResForm<?> deleteApplication(Long applyId, String userReferenceId) {

        applicationService.deleteApplication(applyId, userReferenceId);
        return ResForm.onSuccess(InSuccess.APPLICATION_DELETED, null);
    }

//    @Override
//    public ResForm<?> getApplication(Long applicationId, HttpServletRequest httpServletRequest) {
//        return null;
//    }

    ;
}
