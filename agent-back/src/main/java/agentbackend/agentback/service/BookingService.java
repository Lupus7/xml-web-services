package agentbackend.agentback.service;

import agentbackend.agentback.controller.dto.AdClientDTO;
import agentbackend.agentback.controller.dto.BookDTO;
import agentbackend.agentback.controller.dto.BookingDTO;
import agentbackend.agentback.controller.dto.BundleDTO;
import agentbackend.agentback.model.Ad;
import agentbackend.agentback.model.Booking;
import agentbackend.agentback.model.RequestState;
import agentbackend.agentback.model.User;
import agentbackend.agentback.repository.AdRepository;
import agentbackend.agentback.repository.BookingRepository;
import agentbackend.agentback.repository.UserRepository;
import agentbackend.agentback.soapClient.BookingSoapClient;
import com.car_rent.agent_api.wsdl.ReserveBookingResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    BookingSoapClient bookingSoapClient;

    public boolean reserveBookingRequest(BookDTO bookDTO, String email) throws JSONException {

        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;

        if (bookDTO == null || bookDTO.getAdId() == null || bookDTO.getEndDate() == null || bookDTO.getStartDate() == null || bookDTO.getPlace() == null)
            return false;

        if (bookDTO.getPlace().equals(""))
            return false;

        if (bookDTO.getStartDate().isAfter(bookDTO.getEndDate()))
            return false;

        Optional<Ad> ad = adRepository.findById(bookDTO.getAdId());
        if (!ad.isPresent())
            return false;

        // OTKAZI PRVO SVE ZAHTEVE
        ArrayList<Booking> bookings = bookingRepo.findAllByAd(ad.get().getId());
        for (Booking b1 : bookings) {
            b1.setState(RequestState.CANCELED);
            bookingRepo.save(b1);
        }
        //SACUVAJ OVAJ ZAHTEV SA STATUSOM PAID
        Booking booking = new Booking(bookDTO.getStartDate(), bookDTO.getEndDate(), RequestState.PAID, bookDTO.getPlace(), LocalDateTime.now(), bookDTO.getAdId(), (long) -1);
        BundleDTO bundleDTO = new BundleDTO();
        bundleDTO.getBooks().add(bookDTO);
        ReserveBookingResponse response = bookingSoapClient.reserveBooking(bundleDTO, email);
        booking.setServiceId(response.getId());
        bookingRepo.save(booking);


        return true;
    }

    public boolean acceptBookingRequest(Long id, Principal user) throws JSONException {
        User userr = userRepository.findByEmail(user.getName());
        if (userr == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent())
            return false;

        booking.get().setState(RequestState.PAID);
        bookingRepo.save(booking.get());

        //Deactivate AD
        Optional<Ad> ad = adRepository.findById(booking.get().getAd());
        ad.get().setActive(false);
        adRepository.save(ad.get());

        if (booking.get().getServiceId() != null)
            bookingSoapClient.acceptBooking(booking.get().getServiceId(), user.getName());

        return true;
    }

    public boolean rejectBookingRequest(Long id, Principal user) throws JSONException {

        User userr = userRepository.findByEmail(user.getName());
        if (userr == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() != RequestState.PENDING)
            return false;

        Ad ad = adRepository.getOne(booking.get().getAd());
        if (ad == null) {
            return false;
        }

        if (ad.getOwnerId() == userr.getId()) {
            return false;
        }

        booking.get().setState(RequestState.CANCELED);
        bookingRepo.save(booking.get());


        if (booking.get().getServiceId() != null)
            bookingSoapClient.rejectBooking(booking.get().getServiceId(), user.getName());


        return true;
    }

    public Set<BookingDTO> getAllBookingRequestsFromOthers(String email) {
        Set<BookingDTO> bookingDTOS = new HashSet<>();

        User user = userRepository.findByEmail(email);

        List<Ad> ads = adRepository.findAllByOwnerId(user.getId());
        if (ads == null)
            return bookingDTOS;

        ads.forEach(ad -> {
            bookingRepo.findAllByAd(ad.getId()).forEach(book -> {
                bookingDTOS.add(new BookingDTO(book));
            });
        });

        return bookingDTOS;
    }


    public boolean checkingBookingRequests(String jsonObject, String email) {

        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;

        Long userId = user.getId();

        String adsIds = jsonObject;
        String[] str = adsIds.split(";");
        for (String s : str) {
            for (Booking b : bookingRepo.findAllByAd(Long.parseLong(s))) {
                if (!b.getState().equals(RequestState.CANCELED) && !b.getState().equals(RequestState.PENDING))
                    return false;
            }
        }

        bookingSoapClient.checkingBooking(jsonObject, email);
        return true;
    }

    public boolean deleteCarsBookings(String id, String email) {

        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;


        String[] str = id.split(";");
        for (String s : str) {
            for (Booking b : bookingRepo.findAllByAd(Long.parseLong(s))) {
                b.setState(RequestState.CANCELED);
                bookingRepo.save(b);
            }
        }
        try {
            bookingSoapClient.deleteBooking(id, email);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public BookingDTO getBooking(Long id, String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return null;


        Optional<Booking> booking = bookingRepo.findById(id);
        if (booking.isPresent())
            return new BookingDTO(booking.get());


        return new BookingDTO();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkingEndedState() {

        List<Booking> bookings = bookingRepo.findAllByState(RequestState.PAID);
        LocalDateTime today = LocalDateTime.now();

        for (Booking booking : bookings) {

            if (booking.getEndDate().isBefore(today) || booking.getEndDate().equals(today)) {
                booking.setState(RequestState.ENDED);
                bookingRepo.save(booking);
            }

        }

    }
}
