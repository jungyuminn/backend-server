package club.gach_dong.service;

import club.gach_dong.exception.CustomException;
import club.gach_dong.response.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@RequiredArgsConstructor
public class AuthorizationService {

    @Value("${msa.url.club}")
    private String clubUrl;

    private final static String MEMBER_ID_HEADER_KEY = "X-MEMBER-ID";

    public Long getUserId(HttpServletRequest httpServletRequest){
        String header = httpServletRequest.getHeader(MEMBER_ID_HEADER_KEY);

        if(header!=null){
            return Long.parseLong(header);
        }

        throw new CustomException(ErrorStatus.USER_NOT_FOUND);
//        return null;
    }

    public void getAuthByUserIdAndApplyId(Long userId, Long applyId){

        RestClient restClient = RestClient.create();

        String uri = UriComponentsBuilder.fromHttpUrl(clubUrl)
                .queryParam("userId", userId)
                .queryParam("applyId", applyId)
                .toUriString();

        try {
            Boolean result = restClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(Boolean.class);

            if(Boolean.TRUE.equals(result)){
                return;
            }else{
                throw new CustomException(ErrorStatus._UNAUTHORIZED);
            }
//            return result != null ? result : false;

        } catch (org.springframework.web.client.RestClientException e) {
            System.err.println("REST 클라이언트 오류 발생: " + e.getMessage());
            throw new CustomException(ErrorStatus._UNAUTHORIZED);
//            return false;
        } catch (Exception e) {
            System.err.println("예상치 못한 오류 발생: " + e.getMessage());
            throw new CustomException(ErrorStatus._UNAUTHORIZED);
//            return false;
        }
    }
}
