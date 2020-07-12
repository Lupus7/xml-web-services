package agentbackend.agentback.repository;

import agentbackend.agentback.model.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    List<PriceList> findAllByOwner(String email);
}
