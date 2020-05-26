package carRent.repository;

import carRent.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    ArrayList<Booking> findAllByLoaner(Long id);
    @Query("SELECT * from Booking b WHERE ?1 IN b.ads")
    ArrayList<Booking> findAllByAds(Long id);
}
