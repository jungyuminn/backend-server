package club.gach_dong.repository;

import club.gach_dong.domain.ApplicationDocs;
import club.gach_dong.domain.ApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationDocsRepository extends JpaRepository<ApplicationDocs, Long> {

    List<ApplicationDocs> findAllByApplicationId(Long applicationId);

    void deleteAllByApplicationId(Long applicationId);
}
