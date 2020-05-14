package team10.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team10.admin.models.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findOneByBrandId(Long id);
    Car findOneByModelId(Long id);
    Car findOneByCarClassId(Long id);
    Car findOneByFuelId(Long id);
    Car findOneByTransmissionId(Long id);
}
