package club.gach_dong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import club.gach_dong.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
