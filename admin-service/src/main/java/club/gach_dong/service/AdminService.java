package club.gachdong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import club.gachdong.entity.InviteCode;
import club.gachdong.repository.InviteCodeRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${club.service.url}")
    private String clubServiceUrl;

    private final InviteCodeRepository inviteCodeRepository;


    public InviteCode generateInviteCode(String userReferenceId) {
        String inviteCode = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(1);
        InviteCode newInviteCode = InviteCode.of(userReferenceId, inviteCode, expiryDate);

        return inviteCodeRepository.save(newInviteCode);
    }

    public InviteCode registerInviteCode(String inviteCode, String userReferenceId) {
        InviteCode existingCode = inviteCodeRepository.findByinviteCode(inviteCode);
        if (existingCode == null || existingCode.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 초대 코드입니다.");
        }

        // 항상 성공했다고 가정
        return existingCode;

        /* 실제 게이트웨이 엔드포인트로 변경 필요
        String url = clubServiceUrl + "/some-endpoint";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, userReferenceId, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return existingCode;
            } else {
                throw new IllegalArgumentException("외부 서비스 요청 실패: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("외부 서비스 요청 실패: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("예기치 않은 오류가 발생했습니다: " + e.getMessage(), e);
        }

         */
    }
}