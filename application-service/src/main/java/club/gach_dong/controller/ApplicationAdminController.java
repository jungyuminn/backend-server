package club.gach_dong.controller;

import club.gach_dong.api.ApplicationAdminApiSpecification;
import club.gach_dong.dto.request.ApplicationRequestDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO.ToGetFormInfoAdminDTO;
import club.gach_dong.response.ResForm;
import club.gach_dong.response.status.InSuccess;
import club.gach_dong.service.ApplicationService;
import club.gach_dong.service.AuthorizationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApplicationAdminController implements ApplicationAdminApiSpecification {


    public final ApplicationService applicationService;
    public final AuthorizationService authorizationService;

    @Override
    public ResForm<ApplicationResponseDTO.ToGetFormInfoAdminDTO> getFormInfoAdmin(Long formId,
                                                                                  String userReferenceId) {

        ApplicationResponseDTO.ToGetFormInfoAdminDTO toGetFormInfoAdminDTO = applicationService.getFormInfoAdmin(formId,
                userReferenceId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_GET_ADMIN_INFO, toGetFormInfoAdminDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> createApplicationForm(
            ApplicationRequestDTO.ToCreateApplicationFormDTO createApplicationFormDTO,
            String userReferenceId) {

        ApplicationResponseDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO = applicationService.createApplicationForm(
                createApplicationFormDTO, userReferenceId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_CREATED, toCreateApplicationFormDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> changeApplicationForm(Long formId,
                                                                                            ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO,
                                                                                            String userReferenceId) {

        ApplicationResponseDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO1 = applicationService.changeApplicationForm(
                formId, toCreateApplicationFormDTO, userReferenceId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_CHANGED, toCreateApplicationFormDTO1);
    }

    @Override
    public ResForm<?> deleteApplicationForm(Long formId, String userReferenceId) {

        applicationService.deleteApplicationForm(formId, userReferenceId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_DELETED, null);
    }

    @Override
    public ResForm<?> changeApplicationStatus(ApplicationRequestDTO.ToChangeApplicationStatus toChangeApplicationStatus,
                                              String userReferenceId) {

        applicationService.changeApplicationStatus(userReferenceId, toChangeApplicationStatus);
        return ResForm.onSuccess(InSuccess.APPLICATION_STATUS_CHANGED, null);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToGetApplicationListAdminDTO> getClubApplicationList(Long recruitmentId,
                                                                                               String userReferenceId) {

        ApplicationResponseDTO.ToGetApplicationListAdminDTO toGetApplicationListAdminDTO = applicationService.getApplicationListAdmin(
                userReferenceId, recruitmentId);

        return ResForm.onSuccess(InSuccess._OK, toGetApplicationListAdminDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToGetApplicationDTO> getClubApplication(Long applicationId,
                                                                                  String userReferenceId) {
        ApplicationResponseDTO.ToGetApplicationDTO toGetApplicationDTO = applicationService.getApplicationAdmin(
                userReferenceId, applicationId);
        return ResForm.onSuccess(InSuccess.APPLICATION_GET_SUCCESS, toGetApplicationDTO);
    }

    @Override
    public ResForm<List<ToGetFormInfoAdminDTO>> getClubApplicationFormList(Long clubId, String userReferenceId) {
        List<ApplicationResponseDTO.ToGetFormInfoAdminDTO> toGetFormInfoAdminDTOList = applicationService.toGetFormInfoListAdmin(
                userReferenceId, clubId);

        return ResForm.onSuccess(InSuccess._OK, toGetFormInfoAdminDTOList);
    }
}
