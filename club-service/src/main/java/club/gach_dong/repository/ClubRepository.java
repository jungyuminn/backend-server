package club.gach_dong.repository;

import club.gach_dong.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findAll();

    Optional<Club> findById(Long id);

    @Query("SELECT c FROM Club c JOIN FETCH c.recruitment")
    List<Club> findAllWithRecruitments();

    @Query("SELECT c FROM Club c JOIN FETCH c.admins WHERE c.id = :clubId")
    Optional<Club> findByIdWithAdmins(@Param("clubId") Long clubId);
}
