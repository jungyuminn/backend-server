package club.gach_dong.service;

import club.gach_dong.exception.CustomException;
import club.gach_dong.response.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final static String MEMBER_ID_HEADER_KEY = "X-MEMBER-ID";

    public Long getUserId(HttpServletRequest httpServletRequest){
        String header = httpServletRequest.getHeader(MEMBER_ID_HEADER_KEY);

        if(header!=null){
            return Long.parseLong(header);
        }

        throw new CustomException(ErrorStatus.USER_NOT_FOUND);
//        return null;
    }
}
