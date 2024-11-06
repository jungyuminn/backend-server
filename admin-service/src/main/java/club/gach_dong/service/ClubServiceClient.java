package club.gach_dong.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import test.admin.dto.request.TransferRequest;

@Component
public class ClubServiceClient {

    private final RestTemplate restTemplate;

    public ClubServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getClubNameByUserReferenceId(String userReferenceId) {
        String url = "http://club-service/api/club/name?userReferenceId=" + userReferenceId;

        return restTemplate.getForObject(url, String.class);
    }
}
