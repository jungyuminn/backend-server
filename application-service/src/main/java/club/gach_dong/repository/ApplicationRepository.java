package club.gach_dong.repository;

import club.gach_dong.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByUserIdAndApplyId(String userId, Long applyId);

    List<Application> findAllByUserId(String userId);

    @Modifying
    @Query("UPDATE application a SET a.applicationStatus = :status WHERE a = :application")
    void updateApplicationStatus(@Param("status") String status, @Param("application") Application application);
}
