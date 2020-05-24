package team10.codebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team10.codebook.models.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
}
