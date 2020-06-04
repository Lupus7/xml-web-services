package CarsAdsApp.repository;

import CarsAdsApp.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository  extends JpaRepository<Image, Long> {
    List<Image> findAllByCarId(Long id);
}
