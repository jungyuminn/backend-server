package club.gach_dong.repository;

import club.gach_dong.domain.Application;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByUserIdAndRecruitmentId(String userId, Long recruitmentId);

    List<Application> findAllByUserId(String userId);

    @Modifying
    @Query("UPDATE application a SET a.applicationStatus = :status WHERE a = :application")
    void updateApplicationStatus(@Param("status") String status, @Param("application") Application application);

    @Query("SELECT a FROM application a WHERE a.recruitmentId = :recruitmentId AND a.applicationStatus NOT IN ('TEMPORARY_SAVED')")
    List<Application> findAllByRecruitmentIdAndApplicationStatus(@Param("recruitmentId") Long recruitmentId);

    Optional<Application> findByRecruitmentIdAndApplicationStatusAndUserId(Long recruitmentId, String status,
                                                                           String userId);
}
