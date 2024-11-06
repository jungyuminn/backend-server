package club.gach_dong.gcp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8))
        );

        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build()
                .getService();
    }
}
