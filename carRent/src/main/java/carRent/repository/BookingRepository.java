package carRent.repository;

import carRent.model.Booking;
import carRent.model.RequestState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    ArrayList<Booking> findAllByLoaner(Long id);

    ArrayList<Booking> findAllByAd(Long id);

    List<Booking> findAllByState(RequestState paid);

    Optional<Booking> findByIdAndBundleId(Long id, Long bundle_id);

    List<Booking> findAllByIdAndBundleId(Long adId, Long bundle_id);

    List<Booking> findAllByAdAndBundleId(Long ad, Long bundle_id);

    ArrayList<Booking> findAllByLoanerAndBundleId(Long userId, Long bundle_id);
}
