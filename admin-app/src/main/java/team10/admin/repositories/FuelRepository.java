package team10.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team10.admin.models.Fuel;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long> {
}
