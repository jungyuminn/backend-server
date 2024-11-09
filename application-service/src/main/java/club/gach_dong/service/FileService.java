package club.gach_dong.service;

import club.gach_dong.exception.FileException.FileFormatNotSupportedException;
import club.gach_dong.exception.FileException.FileNameNotFoundException;
import club.gach_dong.exception.FileException.FileNameTooLongException;
import club.gach_dong.exception.FileException.FileTooLargeException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final long MAX_TOTAL_CERTIFICATE_FILE_SIZE = 104857600L;
    private static final Set<String> ALLOWED_CERTIFICATE_FILE_EXTENSIONS = Set.of(".pdf", ".docx", ".xlsx", ".hwp",
            ".jpg", ".png"); // 허용된 확장자 목록
    private static final long MAX_CERTIFICATE_FILE_LENGTH = 30L;

    public void validateFiles(List<MultipartFile> files) {
        long totalCertificateFileSize = 0L;
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();

            //파일 이름 검증
            if (fileName == null) {
                throw new FileNameNotFoundException();
            } else if (fileName.length() > MAX_CERTIFICATE_FILE_LENGTH) {
                throw new FileNameTooLongException();
            } else {
                String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
                totalCertificateFileSize += file.getSize();

                //확장자 검증
                if (!ALLOWED_CERTIFICATE_FILE_EXTENSIONS.contains(fileExtension)) {
                    throw new FileFormatNotSupportedException();
                }
            }
        }

        //모든 파일의 전체 크기 검증
        if (totalCertificateFileSize > MAX_TOTAL_CERTIFICATE_FILE_SIZE) {
            throw new FileTooLargeException();
        }
    }
}
