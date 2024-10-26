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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationRepository applicationRepository;
    private final FileService fileService;
    private final ObjectStorageService objectStorageService;
    private final ObjectStorageServiceConfig objectStorageServiceConfig;
    private final ApplicationDocsRepository applicationDocsRepository;

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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional
    public void deleteApplicationForm(Long formId, HttpServletRequest httpServletRequest){
        //Verify Club Admin Auth with Club_id, User_Id
        //Get userId
        Long userId = 0L;


        Optional<ApplicationForm> applicationFormOptional = applicationFormRepository.findById(formId);
        if(applicationFormOptional.isEmpty()){
            throw new CustomException(ErrorStatus.APPLICATION_FORM_NOT_FOUND);
        }

        ApplicationForm applicationForm = applicationFormOptional.get();

        checkAuth(userId, applicationForm.getApplyId());

        if(applicationForm.getApplicationFormStatus() == ApplicationFormStatus.IN_USE){
            throw new CustomException(ErrorStatus.APPLICATION_FORM_IN_USE);
        }

        applicationFormRepository.deleteById(formId);
    }


    @Transactional
    public ApplicationResponseDTO.ToCreateApplicationFormDTO changeApplicationForm(Long formId, ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO, HttpServletRequest httpServletRequest){

        //Verify Club Admin Auth with Club_id, User_Id
        //Get userId
        Long userId = 0L;

        checkAuth(userId, toCreateApplicationFormDTO.getApplyId());

        Optional<ApplicationForm> applicationFormOptional = applicationFormRepository.findById(formId);
        if(applicationFormOptional.isEmpty()){
            throw new CustomException(ErrorStatus.APPLICATION_FORM_NOT_FOUND);
        }

        ApplicationForm applicationForm = applicationFormOptional.get();

        deleteApplicationForm(formId, httpServletRequest);

        return createApplicationForm(toCreateApplicationFormDTO, httpServletRequest);
    }

    @Transactional
    public ApplicationResponseDTO.ToCreateApplicationDTO createApplication(Long applyId, List<MultipartFile> files, ApplicationRequestDTO.ToApplyClubDTO toApplyClub, HttpServletRequest httpServletRequest){

        //Verify it is valid apply
        //In constructions!!!
        checkValidApply(applyId);

        //Get userId
        Long userId = 0L;

        //Check it is duplicated
        Optional<Application> applicationOptional = applicationRepository.findByUserIdAndApplyId(userId, applyId);
        if(applicationOptional.isPresent()){
            Application application = applicationOptional.get();
            if(!Objects.equals(application.getApplicationStatus(), "TEMP")){
                throw new CustomException(ErrorStatus.APPLICATION_DUPLICATED);
            }
        }

        if(files!=null){
            //업로드 할 파일 검증 (길이, 확장자 등)
            fileService.validateFiles(files);

            if (files.size() > 5) {
                throw new CustomException(ErrorStatus.FILE_TOO_MANY);
            }

            //uploadFiles
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String fileUrl = objectStorageService.uploadObject(objectStorageServiceConfig.getApplicationDocsDir(), uuid, file);

                ApplicationDocs applicationDocs = ApplicationDocs.builder()
                        .applicationId(toApplyClub.getApplyId())
                        .fileName(fileName)
                        .fileUrl(fileUrl)
                        .build();
                applicationDocsRepository.save(applicationDocs);
            }
        }

        Application application = Application.builder()
                .userId(userId)
                .applyId(applyId)
                .applicationFormId(toApplyClub.getApplicationFormId())
                .applicationBody(toApplyClub.getFormBody())
                .applicationStatus(toApplyClub.getStatus())
                .clubName(toApplyClub.getClubName())
                .build();

        if(Objects.equals(application.getApplicationStatus(), "TEMP")){
            deleteApplication(applyId, httpServletRequest);
        }

        Application applicationId = applicationRepository.save(application);

        return ApplicationResponseDTO.ToCreateApplicationDTO.builder()
                .applyId(applicationId.getId())
                .build();
    }

    public void checkValidApply(Long applyId){

        //send to apply

        if(false){
            throw new CustomException(ErrorStatus._BAD_REQUEST);
        }

    }

    @Transactional
    public void deleteApplication(Long applyId, HttpServletRequest httpServletRequest) {

        //Get userId
        Long userId = 0L;

        Optional<Application> applicationOptional = applicationRepository.findByUserIdAndApplyId(userId, applyId);

        //If application not present
        if(applicationOptional.isEmpty()){
            throw new CustomException(ErrorStatus.APPLICATION_NOT_PRESENT);
        }

        Application application = applicationOptional.get();

        //If application is not owner of user
        if(!userId.equals(application.getUserId())){
            throw new CustomException(ErrorStatus.APPLICATION_UNAUTHORIZED);
        }

        //If application couldn't be deleted.
        if(Objects.equals(application.getApplicationStatus(), "SAVED")){
            throw new CustomException(ErrorStatus.APPLICATION_NOT_CHANGEABLE);
        }

        List<ApplicationDocs> applicationDocsList = applicationDocsRepository.findAllByApplicationId(applyId);

        for(ApplicationDocs applicationDocs : applicationDocsList){
            objectStorageService.deleteObject(applicationDocs.getFileUrl());
        }

        applicationDocsRepository.deleteAllByApplicationId(application.getId());

        applicationRepository.delete(application);
    }

    @Transactional
    public ApplicationResponseDTO.ToCreateApplicationDTO changeApplication(Long applyId, List<MultipartFile> files, ApplicationRequestDTO.ToApplyClubDTO toApplyClub, HttpServletRequest httpServletRequest) {
        deleteApplication(applyId, httpServletRequest);
        return createApplication(applyId, files, toApplyClub, httpServletRequest);
    }

    @Transactional(readOnly = true)
    public ApplicationResponseDTO.ToGetApplicationHistoryListDTO getApplicationHistoryList(HttpServletRequest httpServletRequest){

        //Get userId
        Long userId = 0L;

        List<Application> applicationList = applicationRepository.findAllByUserId(userId);

        List<ApplicationResponseDTO.ToGetApplicationHistoryDTO> applicationHistoryDTOs = applicationList.stream()
                .map(application -> ApplicationResponseDTO.ToGetApplicationHistoryDTO.builder()
                        .applicationId(application.getId())
                        .clubName(application.getClubName())
                        .status(application.getApplicationStatus())
                        .build())
                .collect(Collectors.toList());

        return ApplicationResponseDTO.ToGetApplicationHistoryListDTO.builder()
                .toGetApplicationHistoryDTO(applicationHistoryDTOs)
                .build();

    }
}
