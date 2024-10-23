package club.gach_dong.service;

import club.gach_dong.domain.Application;
import club.gach_dong.domain.ApplicationDocs;
import club.gach_dong.domain.ApplicationForm;
import club.gach_dong.domain.ApplicationFormStatus;
import club.gach_dong.dto.request.ApplicationRequestDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO;
import club.gach_dong.exception.CustomException;
import club.gach_dong.gcp.ObjectStorageService;
import club.gach_dong.gcp.ObjectStorageServiceConfig;
import club.gach_dong.repository.ApplicationDocsRepository;
import club.gach_dong.repository.ApplicationFormRepository;
import club.gach_dong.repository.ApplicationRepository;
import club.gach_dong.response.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationFormRepository applicationFormRepository;
    @Transactional
    public ApplicationResponseDTO.ToCreateApplicationFormDTO createApplicationForm(ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO, HttpServletRequest httpServletRequest){

        //Verify Club Admin Auth with Club_id, User_Id
        //Get userId
        Long userId = 0L;

        checkAuth(userId, toCreateApplicationFormDTO.getApplyId());

        ApplicationForm applicationForm = ApplicationForm.builder()
                .applicationFormStatus(ApplicationFormStatus.valueOf(toCreateApplicationFormDTO.getStatus()))
                .formName(toCreateApplicationFormDTO.getFormName())
                .applyId(toCreateApplicationFormDTO.getApplyId())
                .body(toCreateApplicationFormDTO.getFormBody())
                .build();

        ApplicationForm createdApplicationForm = applicationFormRepository.save(applicationForm);

        return ApplicationResponseDTO.ToCreateApplicationFormDTO.builder()
                .applicationFormId(createdApplicationForm.getId())
                .build();
    }

    public void checkAuth(Long userId, Long applyId){
        //Check Auth
        //Communicate with club server to check userId has auth with applyId
        //Club server have to get clubId with applyId and check userId auth with clubId

        if(false){
            throw new CustomException(ErrorStatus.CLUB_UNAUTHORIZED);
        }

    }

    public ApplicationResponseDTO.ToGetFormInfoAdminDTO getFormInfoAdmin(Long formId, HttpServletRequest httpServletRequest){

        //Verify Club Admin Auth with Club_id, User_Id
        //Get userId
        Long userId = 0L;


        Optional<ApplicationForm> applicationFormOptional = applicationFormRepository.findById(formId);
        if(applicationFormOptional.isEmpty()){
            throw new CustomException(ErrorStatus.APPLICATION_FORM_NOT_FOUND);
        }

        ApplicationForm applicationForm = applicationFormOptional.get();

        checkAuth(userId, applicationForm.getApplyId());

        return ApplicationResponseDTO.ToGetFormInfoAdminDTO.builder()
                .formId(applicationForm.getId())
                .formName(applicationForm.getFormName())
                .formBody(applicationForm.getBody())
                .formStatus(String.valueOf(applicationForm.getApplicationFormStatus()))
                .formSettings(null)
                .build();
    }

    public ApplicationResponseDTO.ToGetFormInfoUserDTO getFormInfoUser(Long formId, HttpServletRequest httpServletRequest){

        //Get userId
        Long userId = 0L;


        Optional<ApplicationForm> applicationFormOptional = applicationFormRepository.findById(formId);
        if(applicationFormOptional.isEmpty()){
            throw new CustomException(ErrorStatus.APPLICATION_FORM_NOT_FOUND);
        }

        ApplicationForm applicationForm = applicationFormOptional.get();

        return ApplicationResponseDTO.ToGetFormInfoUserDTO.builder()
                .formId(applicationForm.getId())
                .formName(applicationForm.getFormName())
                .formBody(applicationForm.getBody())
                .build();
    }
}
