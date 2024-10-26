package club.gach_dong.gcp;

import club.gach_dong.exception.CustomException;
import club.gach_dong.response.status.ErrorStatus;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ObjectStorageService {

    private final ObjectStorageServiceConfig objectStorageServiceConfig;
    private final Storage storage;

    private static final Logger logger = LogManager.getLogger(ObjectStorageService.class);


    /**
     * @param directory: ObjectStorageConfig 및 application.yml에 정의되어있는 object storage 내 디렉토리
     * @param objectKey: 업로드할 파일의 이름
     * @param file:      업로드할 파일 객체
     * @return
     */

    public String uploadObject(String directory, String objectKey, MultipartFile file) {

        String fileName = file.getOriginalFilename();

        String contentType = file.getContentType();
        if (contentType == null) {
            throw new CustomException(ErrorStatus.FILE_FORMAT_NOT_FOUND);
        }

        BlobId blobId = BlobId.of(objectStorageServiceConfig.getBucketName(), directory + "/" + objectKey);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(contentType)
                .build();
        try (WriteChannel writer = storage.writer(blobInfo)) {
            byte[] imageData = file.getBytes();
            writer.write(ByteBuffer.wrap(imageData));
            logger.info("Object created");
        } catch (Exception ex) {
            logger.error("Failed to upload object: {}", ex.getMessage(), ex);
            throw new CustomException(ErrorStatus._BAD_REQUEST);
        }

        // Construct the URL to access the uploaded object
        String url = "https://storage.googleapis.com/" + objectStorageServiceConfig.getBucketName() + "/" + directory + "/" + objectKey;
        return url;
    }

    /**
     * @param url: 삭제할 객체 정보, 무조건 삭제할 객체가 '버킷의 root dir/subdir' 내에 있어야 함
     * @return
     */
    
    public void deleteObject(String url) {

        String bucketName = objectStorageServiceConfig.getBucketName();

        String[] extractSegments = extractSegments(url);

        String directory = extractSegments[0];
        String objectKey = extractSegments[1];

        if(directory==null || objectKey==null){
            logger.warn("Url format is Wrong. objectKey: {}, bucketName: {} have to be not null.", objectKey, bucketName);
            //throw new CustomException(ErrorStatus.FILE_DELETE_FAILED_CRITICAL);
            return;
        }

        BlobId blobId = BlobId.of(bucketName, directory + "/" + objectKey);
        Blob blob = storage.get(blobId);

        if (blob == null) {
            logger.warn("The object {} wasn't found in {}", objectKey, bucketName);
            //throw new CustomException(ErrorStatus.FILE_NOT_FOUND);
            return;
        }

        Storage.BlobSourceOption precondition = Storage.BlobSourceOption.generationMatch(blob.getGeneration());

        try {
            boolean deleted = storage.delete(blobId, precondition);
            if (deleted) {
                logger.info("Object {} was deleted from {}", objectKey, bucketName);
            } else {
                logger.error("Failed to delete object {} from {}", objectKey, bucketName);
                //throw new CustomException(ErrorStatus.FILE_DELETE_FAILED);
            }
        } catch (StorageException ex) {
            logger.error("Failed to delete object: {}", ex.getMessage(), ex);
            //throw new CustomException(ErrorStatus.FILE_DELETE_FAILED_CRITICAL);
        }
    }

    public static String[] extractSegments(String url) {
        String[] parts = url.split("/");
        if (parts.length >= 5) {
            return new String[]{parts[4], parts[5]};
        }
        return null;
    }

}
