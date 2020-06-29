package agentbackend.agentback.repository;

import agentbackend.agentback.model.Bundle;
import agentbackend.agentback.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BundleRepository extends JpaRepository<Bundle, Long> {
    Optional<Bundle> findById(Long id);
}
