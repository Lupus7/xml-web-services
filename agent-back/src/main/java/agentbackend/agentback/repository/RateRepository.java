package agentbackend.agentback.repository;

import agentbackend.agentback.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findAllByCarId(Long id);
}
