package club.gach_dong.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class GcpStorageConfig {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String credentialsPath;

    @Bean
    public Storage gcpStorage() throws IOException {
        try (InputStream keyFileStream = getClass().getClassLoader().getResourceAsStream(credentialsPath)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(keyFileStream);
            return StorageOptions.newBuilder()
                    .setCredentials(credentials)
                    .build()
                    .getService();
        }
    }

    @Bean
    public String bucketName() {
        return bucketName;
    }
}
