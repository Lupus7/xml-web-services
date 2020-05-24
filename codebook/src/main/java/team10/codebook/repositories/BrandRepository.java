package team10.codebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team10.codebook.models.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByName(String brand);
}
