package club.gach_dong.service;

import club.gach_dong.exception.ApplicationException.ApplicationUnauthorizedException;
import club.gach_dong.exception.ClubException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@RequiredArgsConstructor
public class AuthorizationService {

    @Value("${msa.url.club}")
    private String clubUrl;

    @Value("${msa.url.auth}")
    private String authUrl;

    @Value("${msa.url.apply}")
    private String applyUrl;

    private final static String MEMBER_ID_HEADER_KEY = "X-USER-REFERENCE-ID";

    public final RestClient restClient;

//    public String getUserId(HttpServletRequest httpServletRequest) {
//        String header = httpServletRequest.getHeader(MEMBER_ID_HEADER_KEY);
//
//        if (header != null) {
//            return header;
//        }
//
//        throw new UserException.UserNotFound();
////        return null;
//    }

    public void getAuthByUserIdAndApplyId(String userId, Long applyId) {

        String uri = UriComponentsBuilder.fromHttpUrl(clubUrl)
                .path("/" + authUrl)
                .queryParam("userId", userId)
                .queryParam("applyId", applyId)
                .toUriString();

        try {
            Boolean result = restClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(Boolean.class);

            if (Boolean.FALSE.equals(result)) {
                throw new ClubException.ClubAdminUnauthorizedException();
            }
//            return result != null ? result : false;

        } catch (RestClientException e) {
            System.err.println("REST 클라이언트 오류 발생: " + e.getMessage());
            throw new ClubException.ClubAdminCommunicateFailedException();
//            return false;
        } catch (Exception e) {
            System.err.println("예상치 못한 오류 발생: " + e.getMessage());
            throw new ClubException.ClubAdminCommunicateFailedException();
//            return false;
        }
    }

    public void getApplyIsValid(Long applyId) {

        String uri = UriComponentsBuilder.fromHttpUrl(clubUrl)
                .path("/" + applyUrl + "/{applyId}")
                .buildAndExpand(applyId)
                .toUriString();

        try {
            Boolean result = restClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(Boolean.class);

            if (Boolean.FALSE.equals(result)) {
                throw new ApplicationUnauthorizedException();
            }
//            return result != null ? result : false;

        } catch (RestClientException e) {
            System.err.println("REST 클라이언트 오류 발생: " + e.getMessage());
            throw new ClubException.ClubAdminCommunicateFailedException();
//            return false;
        } catch (Exception e) {
            System.err.println("예상치 못한 오류 발생: " + e.getMessage());
            throw new ClubException.ClubAdminCommunicateFailedException();
//            return false;
        }
    }


}
