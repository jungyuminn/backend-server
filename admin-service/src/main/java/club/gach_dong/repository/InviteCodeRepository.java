package club.gach_dong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import club.gach_dong.entity.InviteCode;

@Repository
public interface InviteCodeRepository extends JpaRepository<InviteCode, Long> {
    InviteCode findByinviteCode(String inviteCode);
}
