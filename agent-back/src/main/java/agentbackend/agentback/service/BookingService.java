package agentbackend.agentback.service;

import agentbackend.agentback.controller.dto.AdClientDTO;
import agentbackend.agentback.controller.dto.BookDTO;
import agentbackend.agentback.controller.dto.BookingDTO;
import agentbackend.agentback.controller.dto.BundleDTO;
import agentbackend.agentback.model.*;
import agentbackend.agentback.repository.AdRepository;
import agentbackend.agentback.repository.BookingRepository;
import agentbackend.agentback.repository.BundleRepository;
import agentbackend.agentback.repository.UserRepository;
import agentbackend.agentback.soapClient.BookingSoapClient;
import com.car_rent.agent_api.wsdl.BookingDetails;
import com.car_rent.agent_api.wsdl.GetBookingsResponse;
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
    private AdService adService;
    @Autowired
    BookingSoapClient bookingSoapClient;
    @Autowired
    BundleRepository bundleRepository;

    public HashMap<Long, Booking> reserveBookingRequest(BundleDTO bundleDTO, String email) {

        User user = userRepository.findByEmail(email);
        if (user == null)
            return new HashMap<Long, Booking>();

        ReserveBookingResponse response = bookingSoapClient.reserveBooking(bundleDTO, email);
        int i = 0;

        HashMap<Long, Booking> reservedBookings = new HashMap<>();


        Bundle bundle = new Bundle();
        bundle.setLoaner(null);
        bundle.setServiceId(response.getBundleId());
        if (bundleDTO.getBooks().size() > 1) {
            for (BookDTO bookDTO : bundleDTO.getBooks()) {

                if (bookDTO == null || bookDTO.getAdId() == null || bookDTO.getEndDate() == null || bookDTO.getStartDate() == null || bookDTO.getPlace() == null)
                    return new HashMap<>();

                if (bookDTO.getPlace().equals(""))
                    return new HashMap<>();

                if (bookDTO.getStartDate().isAfter(bookDTO.getEndDate()))
                    return new HashMap<>();

                Optional<Ad> ad = adRepository.findById(bookDTO.getAdId());
                if (!ad.isPresent())
                    return new HashMap<>();
                if (!ad.get().isActive())
                    return new HashMap<>();

                Booking booking = new Booking(bookDTO.getStartDate(), bookDTO.getEndDate(), RequestState.PAID, bookDTO.getPlace(), LocalDateTime.now(), ad.get(), null);
                booking.setServiceId(response.getBookings().get(i));
                ++i;
                bundle.getBookings().add(booking);
                bookingRepo.save(booking);
                reservedBookings.put(booking.getId(), booking);

            }

            bundleRepository.save(bundle);
        } else {

            if (bundleDTO.getBooks().get(0) == null || bundleDTO.getBooks().get(0).getAdId() == null || bundleDTO.getBooks().get(0).getEndDate() == null || bundleDTO.getBooks().get(0).getStartDate() == null || bundleDTO.getBooks().get(0).getPlace() == null)
                return new HashMap<>();

            if (bundleDTO.getBooks().get(0).getPlace().equals(""))
                return new HashMap<>();

            if (bundleDTO.getBooks().get(0).getStartDate().isAfter(bundleDTO.getBooks().get(0).getEndDate()))
                return new HashMap<>();

            // provera da li ad postoji odnosno da li je aktivan

            Optional<Ad> ad = adRepository.findById(bundleDTO.getBooks().get(0).getAdId());
            if (!ad.isPresent())
                return new HashMap<>();
            if (!ad.get().isActive())
                return new HashMap<>();

            Booking booking = new Booking(bundleDTO.getBooks().get(0).getStartDate(), bundleDTO.getBooks().get(0).getEndDate(), RequestState.PAID, bundleDTO.getBooks().get(0).getPlace(), LocalDateTime.now(), ad.get(), null);
            booking.setServiceId(response.getBookings().get(0));
            bookingRepo.save(booking);
            reservedBookings.put(booking.getId(), booking);

        }

        //canceluj sve ostale bookinge koju su vezani za taj ad
        Long adId = null;
        for (Booking b : reservedBookings.values()) {
            adId = b.getAd().getId();
            List<Booking> bookings = bookingRepo.findAllByAd(b.getAd());
            for (Booking b1 : bookings) {
                if (reservedBookings.containsKey(b1.getId()))
                    continue;
                b1.setState(RequestState.CANCELED);
                bookingRepo.save(b1);
            }
        }

        adService.deactivateAd(adId, email);


        return reservedBookings;
    }

    public boolean acceptBookingRequest(Long id, String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent())
            return false;

        if (booking.get().getAd() == null || !booking.get().getAd().getOwner().equals(user.getEmail()))
            return false;

        booking.get().setState(RequestState.PAID);
        bookingRepo.save(booking.get());

        booking.get().getAd().setActive(false);
        adRepository.save(booking.get().getAd());

        List<Booking> bookings = bookingRepo.findAllByAd(booking.get().getAd());
        for (Booking b : bookings) {
            if (b.getState().equals(RequestState.PENDING) && b.getId() != id) {
                b.setState(RequestState.CANCELED);
                bookingRepo.save(b);
            }

        }

        if (booking.get().getServiceId() != null)
            bookingSoapClient.acceptBooking(booking.get().getServiceId(), user.getEmail());

        return true;
    }

    public boolean rejectBookingRequest(Long id, String email) {

        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() != RequestState.PENDING)
            return false;

        if (booking.get().getAd() == null || !booking.get().getAd().getOwner().equals(user.getEmail()))
            return false;

        booking.get().setState(RequestState.CANCELED);
        bookingRepo.save(booking.get());

        if (booking.get().getServiceId() != null)
            bookingSoapClient.rejectBooking(booking.get().getServiceId(), user.getEmail());


        return true;
    }

    public Set<BookingDTO> getAllReceivedBookingRequests(String email) {

        Set<BookingDTO> bookingDTOS = new HashSet<>();
        User user = userRepository.findByEmail(email);
        if (user == null)
            return bookingDTOS;

        GetBookingsResponse serviceBookings = bookingSoapClient.getBookings(email);
        for (BookingDetails bookDetail : serviceBookings.getResponse()) {
            Booking b = bookingRepo.findByServiceIdAndBundleId(bookDetail.getId(), null);
            if (b == null) {
                Ad ad = adRepository.findByServiceId(bookDetail.getAd());
                Booking booking = new Booking(bookDetail, ad);
                bookingRepo.save(booking);
            } else {
                if (returnInt(b.getState()) != bookDetail.getState()) {
                    b.setState(returnState(bookDetail.getState()));
                    bookingRepo.save(b);
                }
            }
        }


        List<Ad> ads = adRepository.findAllByOwner(user.getEmail());
        if (ads == null)
            return bookingDTOS;

        for (Ad ad : ads) {
            List<Booking> bookings = bookingRepo.findAllByAdAndBundleId(ad, null);
            for (Booking b : bookings)
                bookingDTOS.add(new BookingDTO(b));

        }

        return bookingDTOS;
    }


    public boolean checkingBookingRequests(String jsonObject, String email) {

        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;

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
        } catch (Exception e) {
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

    public int returnInt(RequestState state) {
        if (state.equals(RequestState.PENDING))
            return 0;
        else if (state.equals(RequestState.PAID))
            return 1;
        else if (state.equals(RequestState.CANCELED))
            return 2;
        else
            return 3;
    }

    public RequestState returnState(int number) {
        if (number == 0)
            return RequestState.PENDING;
        else if (number == 1)
            return RequestState.PAID;
        else if (number == 2)
            return RequestState.CANCELED;
        else
            return RequestState.ENDED;
    }
}
