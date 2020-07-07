package agentbackend.agentback.repository;

import agentbackend.agentback.model.Ad;
import agentbackend.agentback.model.Booking;
import agentbackend.agentback.model.RequestState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    ArrayList<Booking> findAllByLoaner(Long id);
    ArrayList<Booking> findAllByAd(Long id);
    List<Booking> findAllByAd(Ad ad);
    List<Booking> findAllByAdAndBundleId(Ad ad, Object o);


    List<Booking> findAllByState(RequestState paid);

    Booking findByIdAndBundleId(Long id, Object o);

    Booking findByServiceIdAndBundleId(long id, Object o);

    List<Booking> findAllByAdAndBundleIdNotNull(Ad ad);
}
