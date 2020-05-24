package team10.codebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team10.codebook.models.Fuel;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long> {
}
