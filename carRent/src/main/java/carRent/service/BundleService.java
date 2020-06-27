package carRent.service;

import carRent.model.Booking;
import carRent.model.Bundle;
import carRent.model.RequestState;
import carRent.proxy.CarsAdsProxy;
import carRent.proxy.UserProxy;
import carRent.repository.BookingRepository;
import carRent.repository.BundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BundleService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    UserProxy userProxy;

    @Autowired
    CarsAdsProxy carsAdsProxy;

    public boolean acceptBundleRequest(Long id, String name) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(name);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Bundle> bundle = bundleRepository.findById(id);
        if (!bundle.isPresent())
            return false;

        List<Long> ads = new ArrayList<>();

        for (Booking b : bundle.get().getBookings()) {
            b.setState(RequestState.PAID);
            bookingRepo.save(b);
            carsAdsProxy.deactivateAd(b.getAd(), name + ";MASTER");
            ads.add(b.getAd());
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


    public boolean rejectBundleRequest(Long id, String user) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(user);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Bundle> bundle = bundleRepository.findById(id);
        if (!bundle.isPresent())
            return false;

        for (Booking b : bundle.get().getBookings()) {
            if (b.getState().equals(RequestState.PENDING)) {
                b.setState(RequestState.CANCELED);
                bookingRepo.save(b);
            }
        }

        return true;
    }

    public boolean cancelBundleRequest(Long id, String name) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(name);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Long userId = userIdResponse.getBody();

        Optional<Bundle> bundle = bundleRepository.findById(id);
        if (bundle == null || !bundle.isPresent())
            return false;

        for (Booking booking : bundle.get().getBookings()) {
            if (booking.getState() == RequestState.PAID || booking.getState() == RequestState.ENDED || userId != booking.getLoaner())
                return false;

            booking.setState(RequestState.CANCELED);
            bookingRepo.save(booking);
        }

        return true;
    }
}
