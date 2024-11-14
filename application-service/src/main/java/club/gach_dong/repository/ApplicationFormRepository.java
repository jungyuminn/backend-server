package club.gach_dong.repository;

import club.gach_dong.domain.ApplicationForm;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long> {
    List<ApplicationForm> findAllByClubId(Long clubId);

}
