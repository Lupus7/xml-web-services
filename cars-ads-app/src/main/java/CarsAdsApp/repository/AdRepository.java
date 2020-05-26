package CarsAdsApp.repository;

import CarsAdsApp.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AdRepository extends JpaRepository<Ad,Long> {
    ArrayList<Ad> findAllByOwner(Long id);
    ArrayList<Ad> findAllByOwnerAndActive(Long id, boolean activate);
}
