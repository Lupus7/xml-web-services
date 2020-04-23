package team10.authenticationapp.repositories;

import org.springframework.stereotype.Repository;
import team10.authenticationapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
