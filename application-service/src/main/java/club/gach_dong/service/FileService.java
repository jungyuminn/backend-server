package club.gach_dong.service;

import club.gach_dong.exception.CustomException;
import club.gach_dong.response.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final long MAX_TOTAL_CERTIFICATE_FILE_SIZE = 104857600L;
    private static final Set<String> ALLOWED_CERTIFICATE_FILE_EXTENSIONS = Set.of(".pdf", ".docx", ".xlsx", ".hwp", ".jpg", ".png"); // 허용된 확장자 목록
    private static final long MAX_CERTIFICATE_FILE_LENGTH = 30L;

    public void validateCertificateFiles(List<MultipartFile> certificateFiles) throws CustomException {
        long totalCertificateFileSize = 0L;
        for (MultipartFile certificateFile : certificateFiles) {
            String fileName = certificateFile.getOriginalFilename();

            //파일 이름 검증
            if (fileName == null) {
                throw new CustomException(ErrorStatus.FILE_NAME_NOT_FOUND);
            } else if (fileName.length() > MAX_CERTIFICATE_FILE_LENGTH) {
                throw new CustomException(ErrorStatus.FILE_NAME_TOO_LONG);
            } else {
                String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
                totalCertificateFileSize += certificateFile.getSize();

                //확장자 검증
                if (!ALLOWED_CERTIFICATE_FILE_EXTENSIONS.contains(fileExtension)) {
                    throw new CustomException(ErrorStatus.FILE_FORMAT_NOT_SUPPORTED);
                }
            }
        }

        //모든 파일의 전체 크기 검증
        if (totalCertificateFileSize > MAX_TOTAL_CERTIFICATE_FILE_SIZE) {
            throw new CustomException(ErrorStatus.FILE_TOO_LARGE);
        }
    }
}
