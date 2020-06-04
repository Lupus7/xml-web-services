package carRent.repository;

import carRent.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findById(Long id);

    Optional<Cart> findByUserId(Long userId);
}
