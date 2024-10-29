package club.gach_dong.gcp;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Getter
@Configuration
public class ObjectStorageServiceConfig {
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.project-id}")
    private String projectId;

    @Value("${spring.cloud.gcp.storage.path.applicationDocs}")
    private String applicationDocsDir;

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String credentialsJson;

    @Bean
    public Storage storage() throws IOException {

        ClassPathResource resource = new ClassPathResource(credentialsJson);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());


        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build()
                .getService();
    }
}
