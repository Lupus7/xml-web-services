package team10.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team10.admin.models.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
