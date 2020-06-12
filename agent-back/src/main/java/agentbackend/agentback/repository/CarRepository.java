package agentbackend.agentback.repository;

import agentbackend.agentback.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByOwner(String owner);
    List<Car> findAllByBrand(String brand);
    List<Car> findAllByModel(String model);
    List<Car> findAllByCarClass(String carClass);
    List<Car> findAllByFuel(String fuel);
    List<Car> findAllByTransmission(String transmission);
    List<Car> findTopByOrderByIdDesc();
}
