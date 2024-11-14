package club.gach_dong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import club.gach_dong.entity.InviteCode;
import club.gach_dong.repository.InviteCodeRepository;

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

    public InviteCode generateInviteCode(String userReferenceId, Long clubId) {
        String inviteCode = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(1);
        InviteCode newInviteCode = InviteCode.of(userReferenceId, inviteCode, expiryDate, clubId);

        return inviteCodeRepository.save(newInviteCode);
    }

    public InviteCode registerInviteCode(String inviteCode, String userReferenceId) {
        InviteCode existingCode = inviteCodeRepository.findByinviteCode(inviteCode);
        if (existingCode == null || existingCode.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 초대 코드입니다.");
        }

        Long clubId = existingCode.getClubId();

        String url = clubServiceUrl;

        try {
            ResponseEntity<Boolean> response = restTemplate.postForEntity(url, clubId, Boolean.class);

            if (!response.getStatusCode().is2xxSuccessful() || !response.getBody()) {
                throw new IllegalArgumentException("동아리 관리자 권한 부여 실패");
            } else {
                System.out.println("동아리 관리자 권한 부여 성공: " + clubId);
            }
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("동아리 관리자 권한 부여 요청 실패: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("예기치 않은 오류가 발생했습니다: " + e.getMessage(), e);
        }

        return existingCode;
    }
}
