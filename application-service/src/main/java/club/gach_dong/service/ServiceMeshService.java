package club.gach_dong.service;

import club.gach_dong.dto.response.AuthResponseDTO;
import club.gach_dong.exception.UserException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ServiceMeshService {
    @Value("${msa.url.userDetail}")
    private String userDetailUrl;

    public final RestClient restClient;

    public List<AuthResponseDTO.getUserProfile> getUserProfiles(List<String> userIds) {
        String uri = UriComponentsBuilder.fromHttpUrl(userDetailUrl)
                .path("/profile")
                .toUriString();

        Map<String, List<String>> requestBody = new HashMap<>();
        requestBody.put("userReferenceId", userIds);

        try {
            return restClient.post()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer ")
                    .body(requestBody)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<AuthResponseDTO.getUserProfile>>() {
                    });

        } catch (RestClientException e) {
            System.err.println("REST 클라이언트 오류 발생: " + e.getMessage());
            throw new UserException.UserNotFound();
        } catch (Exception e) {
            System.err.println("예상치 못한 오류 발생: " + e.getMessage());
            throw new UserException.UserNotFound();
        }
    }
}
