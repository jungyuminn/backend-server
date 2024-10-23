package club.gach_dong.repository;

import club.gach_dong.domain.ApplicationDocs;
import club.gach_dong.domain.ApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationDocsRepository extends JpaRepository<ApplicationDocs, Long> {

}
