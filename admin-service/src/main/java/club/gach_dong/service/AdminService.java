package club.gach_dong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import club.gach_dong.entity.Admin;
import club.gach_dong.entity.InviteCode;
import club.gach_dong.repository.AdminRepository;
import club.gach_dong.client.ClubServiceClient;
import club.gach_dong.repository.InviteCodeRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final ClubServiceClient clubServiceClient;
    private final InviteCodeRepository inviteCodeRepository;

    public String getClubNameByUserReferenceId(String userReferenceId) {
        String clubName = clubServiceClient.getClubNameByUserReferenceId(userReferenceId);

        if (clubName != null) {
            Admin admin = Admin.of(userReferenceId, clubName);
            adminRepository.save(admin);
        }

        return clubName;
    }

    public String transferClub(String currentOwnerId, String targetUserReferenceId) {
        Optional<Admin> currentOwner = adminRepository.findByUserReferenceId(currentOwnerId);

        if (currentOwner.isPresent()) {
            String clubName = currentOwner.get().getClubName();

            adminRepository.delete(currentOwner.get());

            Admin newOwner = Admin.of(targetUserReferenceId, clubName);
            adminRepository.save(newOwner);

            return "동아리가 성공적으로 이전되었습니다.";
        } else {
            throw new RuntimeException("현재 소유자를 찾을 수 없습니다.");
        }
    }

    public String generateInviteCode(String userReferenceId) {
        String clubName = adminRepository.findByUserReferenceId(userReferenceId)
                .map(Admin::getClubName)
                .orElseThrow(() -> new RuntimeException("동아리 정보를 찾을 수 없습니다."));

        String inviteCode = UUID.randomUUID().toString();

        InviteCode code = new InviteCode();
        code.setClubName(clubName);
        code.setCode(inviteCode);
        code.setExpiryDate(LocalDateTime.now().plusHours(24));
        inviteCodeRepository.save(code);

        return inviteCode;
    }

    public boolean registerInviteCode(String userReferenceId, String inviteCode) {
        String clubName = adminRepository.findByUserReferenceId(userReferenceId)
                .map(Admin::getClubName)
                .orElse(null);

        InviteCode inviteCodeEntity = inviteCodeRepository.findByCode(inviteCode);

        if (inviteCodeEntity == null || !inviteCodeEntity.getClubName().equals(clubName) ||
                inviteCodeEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        Admin newAdmin = Admin.of(userReferenceId, clubName);
        adminRepository.save(newAdmin);

        return true;
    }
}
