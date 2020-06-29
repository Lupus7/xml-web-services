package agentbackend.agentback.repository;

import agentbackend.agentback.model.Car;
import agentbackend.agentback.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository  extends JpaRepository<Image, Long> {
    List<Image> findAllByCarId(Long CarId);

    List<Image> findAllByCar(Car car);
}
