package carRent.repository;

import carRent.model.Bundle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BundleRepository extends JpaRepository<Bundle,Long> {
    List<Bundle> findAllByLoaner(Long body);
}
