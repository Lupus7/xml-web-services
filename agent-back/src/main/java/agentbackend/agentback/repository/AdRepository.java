package agentbackend.agentback.repository;

import agentbackend.agentback.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AdRepository extends JpaRepository<Ad,Long> {
    ArrayList<Ad> findAllByOwnerId(Long id);
    ArrayList<Ad> findAllByOwnerIdAndActive(Long id, boolean activate);
    Optional<Ad> findById(Long id);

    List<Ad> findAllByCarId(Long id);
}
