package agentbackend.agentback.repository;

import agentbackend.agentback.model.Ad;
import agentbackend.agentback.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AdRepository extends JpaRepository<Ad,Long> {
    ArrayList<Ad> findAllByOwner(String owner);
    ArrayList<Ad> findAllByOwnerAndActive(String owner, boolean activate);
    Optional<Ad> findById(Long id);

    List<Ad> findAllByCarId(Long id);

    List<Ad> findAllByActive(boolean b);

    List<Ad> findAllByCar(Car car);
}
