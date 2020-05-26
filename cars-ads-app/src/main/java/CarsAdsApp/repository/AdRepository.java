package CarsAdsApp.repository;

import CarsAdsApp.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AdRepository extends JpaRepository<Ad,Long> {
    ArrayList<Ad> findAllByOwnerId(Long id);
    ArrayList<Ad> findAllByOwnerIdAndActive(Long id, boolean activate);
}
