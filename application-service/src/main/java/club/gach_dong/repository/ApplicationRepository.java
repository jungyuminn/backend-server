package club.gach_dong.repository;

import club.gach_dong.domain.Application;
import club.gach_dong.domain.ApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByUserIdAndApplyId(Long userId, Long applyId);
}
