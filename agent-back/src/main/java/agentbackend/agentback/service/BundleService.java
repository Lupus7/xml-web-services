package agentbackend.agentback.service;

import agentbackend.agentback.model.Booking;
import agentbackend.agentback.model.Bundle;
import agentbackend.agentback.model.RequestState;
import agentbackend.agentback.model.User;
import agentbackend.agentback.repository.BookingRepository;
import agentbackend.agentback.repository.BundleRepository;
import agentbackend.agentback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BundleService {

    @Autowired
    BundleRepository bundleRepository;

    @Autowired
    BookingRepository bookingRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdService adService;

    public boolean acceptBundleRequest(Long id, String name) {

        User user = userRepository.findByEmail(name);
        if (user == null)
            return false;

        Optional<Bundle> bundle = bundleRepository.findById(id);
        if (!bundle.isPresent() || !user.getEmail().equals(bundle.get().getLoaner()))
            return false;

        List<Long> ads = new ArrayList<>();

        for (Booking b : bundle.get().getBookings()) {
            b.setState(RequestState.PAID);
            bookingRepo.save(b);
            adService.deactivateAd(b.getAd().getId(), name + ";MASTER");
            ads.add(b.getAd().getId());
        }


        for (Long adId : ads) {
            List<Booking> bookings = bookingRepo.findAllByAd(adId);
            for (Booking b : bookings) {
                if (!bundle.get().getBookings().contains(b)) {
                    b.setState(RequestState.CANCELED);
                    bookingRepo.save(b);
                }

            }

        }

        return true;
    }


    public boolean rejectBundleRequest(Long id, String name) {

        User user = userRepository.findByEmail(name);
        if (user == null)
            return false;

        Optional<Bundle> bundle = bundleRepository.findById(id);
        if (!bundle.isPresent() || !user.getEmail().equals(bundle.get().getLoaner()))
            return false;

        for (Booking b : bundle.get().getBookings()) {
            if (b.getState().equals(RequestState.PENDING)) {
                b.setState(RequestState.CANCELED);
                bookingRepo.save(b);
            }
        }

        return true;
    }

}
