package carRent.service;

import carRent.model.Booking;
import carRent.model.Bundle;
import carRent.model.RequestState;
import carRent.model.dto.AdClientDTO;
import carRent.model.dto.BookDTO;
import carRent.model.dto.BookingDTO;
import carRent.model.dto.BundleDTO;
import carRent.proxy.CarsAdsProxy;
import carRent.proxy.UserProxy;
import carRent.repository.BookingRepository;
import carRent.repository.BundleRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    UserProxy userProxy;

    @Autowired
    CarsAdsProxy carsAdsProxy;

    public boolean createBookingRequest(BundleDTO bundleDTO, String email) throws JSONException {


        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Long userId = userIdResponse.getBody();

        Bundle bundle = new Bundle();

        if (bundleDTO.getBooks().size() > 1) {
            for (BookDTO bookDTO : bundleDTO.getBooks()) {

                if (bookDTO == null || bookDTO.getAdId() == null || bookDTO.getEndDate() == null || bookDTO.getStartDate() == null || bookDTO.getPlace() == null)
                    return false;

                if (bookDTO.getPlace().equals(""))
                    return false;

                if (bookDTO.getStartDate().isAfter(bookDTO.getEndDate()))
                    return false;

                ResponseEntity<Boolean> check = carsAdsProxy.getAdById(bookDTO.getAdId(), email + ";MASTER");
                if (check == null || check.getBody() == null || !check.getBody())
                    return false;

                // provera da client nmz sam svoje da rezervise
                ResponseEntity<Long> ownerId = carsAdsProxy.getOwnerById(bookDTO.getAdId(), email + ";MASTER");
                if (ownerId == null || ownerId.getBody() == null || ownerId.getBody().longValue() == userId)
                    return false;

                Booking booking = new Booking(bookDTO.getStartDate(), bookDTO.getEndDate(), RequestState.PENDING, bookDTO.getPlace(), LocalDateTime.now(), bookDTO.getAdId(), userId);
                bundle.getBookings().add(booking);

                bookingRepo.save(booking);


                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (booking.getState().equals(RequestState.PENDING)) {
                                    booking.setState(RequestState.CANCELED);
                                    bookingRepo.save(booking);
                                    cancel();
                                }
                            }
                        },
                        24 * 60 * 60 * 1000
                );


            }
            bundleRepository.save(bundle);
        } else {
            if (bundleDTO.getBooks().get(0) == null || bundleDTO.getBooks().get(0).getAdId() == null || bundleDTO.getBooks().get(0).getEndDate() == null || bundleDTO.getBooks().get(0).getStartDate() == null || bundleDTO.getBooks().get(0).getPlace() == null)
                return false;

            if (bundleDTO.getBooks().get(0).getPlace().equals(""))
                return false;

            if (bundleDTO.getBooks().get(0).getStartDate().isAfter(bundleDTO.getBooks().get(0).getEndDate()))
                return false;

            ResponseEntity<Boolean> check = carsAdsProxy.getAdById(bundleDTO.getBooks().get(0).getAdId(), email + ";MASTER");
            if (check == null || check.getBody() == null || !check.getBody())
                return false;

            // provera da client nmz sam svoje da rezervise
            ResponseEntity<Long> ownerId = carsAdsProxy.getOwnerById(bundleDTO.getBooks().get(0).getAdId(), email + ";MASTER");
            if (ownerId == null || ownerId.getBody() == null || ownerId.getBody().longValue() == userId)
                return false;

            Booking booking = new Booking(bundleDTO.getBooks().get(0).getStartDate(), bundleDTO.getBooks().get(0).getEndDate(), RequestState.PENDING, bundleDTO.getBooks().get(0).getPlace(), LocalDateTime.now(), bundleDTO.getBooks().get(0).getAdId(), userId);
            bookingRepo.save(booking);


            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (booking.getState().equals(RequestState.PENDING)) {
                                booking.setState(RequestState.CANCELED);
                                bookingRepo.save(booking);
                                cancel();
                            }
                        }
                    },
                    24 * 60 * 60 * 1000
            );

        }


        bundleDTO.getBooks().forEach(book -> {
            try {
                cartService.deleteAdToCart(book.getAdId(), email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        return true;
    }

    public boolean reserveBookingRequest(BundleDTO bundleDTO, String email) throws JSONException {

        HashMap<Long, Booking> reservedBookings = new HashMap<>();

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Long userId = userIdResponse.getBody();

        if (bundleDTO.getLoaner().equals(""))
            return false;

        ResponseEntity<Long> loanerIdRes = userProxy.getUserId(bundleDTO.getLoaner());
        if (loanerIdRes == null || loanerIdRes.getBody() == null)
            return false;

        Bundle bundle = new Bundle();
        if (bundleDTO.getBooks().size() > 1) {
            for (BookDTO bookDTO : bundleDTO.getBooks()) {

                if (bookDTO == null || bookDTO.getAdId() == null || bookDTO.getEndDate() == null || bookDTO.getStartDate() == null || bookDTO.getPlace() == null)
                    return false;

                if (bookDTO.getPlace().equals(""))
                    return false;

                if (bookDTO.getStartDate().isAfter(bookDTO.getEndDate()))
                    return false;

                ResponseEntity<Boolean> check = carsAdsProxy.getAdById(bookDTO.getAdId(), email + ";MASTER");
                if (check == null || check.getBody() == null || !check.getBody())
                    return false;

                // provera da client nmz sam svoje da rezervise
                ResponseEntity<Long> ownerId = carsAdsProxy.getOwnerById(bookDTO.getAdId(), email + ";MASTER");
                if (ownerId == null || ownerId.getBody() == null || ownerId.getBody().longValue() == userId)
                    return false;

                Booking booking = new Booking(bookDTO.getStartDate(), bookDTO.getEndDate(), RequestState.PAID, bookDTO.getPlace(), LocalDateTime.now(), bookDTO.getAdId(), (long) -1);
                bundle.getBookings().add(booking);
                bookingRepo.save(booking);
                reservedBookings.put(booking.getId(), booking);

            }

            bundleRepository.save(bundle);
        } else {

            if (bundleDTO.getBooks().get(0) == null || bundleDTO.getBooks().get(0).getAdId() == null || bundleDTO.getBooks().get(0).getEndDate() == null || bundleDTO.getBooks().get(0).getStartDate() == null || bundleDTO.getBooks().get(0).getPlace() == null)
                return false;

            if (bundleDTO.getBooks().get(0).getPlace().equals(""))
                return false;

            if (bundleDTO.getBooks().get(0).getStartDate().isAfter(bundleDTO.getBooks().get(0).getEndDate()))
                return false;

            ResponseEntity<Boolean> check = carsAdsProxy.getAdById(bundleDTO.getBooks().get(0).getAdId(), email + ";MASTER");
            if (check == null || check.getBody() == null || !check.getBody())
                return false;

            // provera da client nmz sam svoje da rezervise
            ResponseEntity<Long> ownerId = carsAdsProxy.getOwnerById(bundleDTO.getBooks().get(0).getAdId(), email + ";MASTER");
            if (ownerId == null || ownerId.getBody() == null || ownerId.getBody().longValue() == userId)
                return false;

            Booking booking = new Booking(bundleDTO.getBooks().get(0).getStartDate(), bundleDTO.getBooks().get(0).getEndDate(), RequestState.PAID, bundleDTO.getBooks().get(0).getPlace(), LocalDateTime.now(), bundleDTO.getBooks().get(0).getAdId(), (long) -1);
            bundle.getBookings().add(booking);
            bookingRepo.save(booking);
            reservedBookings.put(booking.getId(), booking);

        }

        //canceluj sve ostale bookinge koju su vezani za taj ad

        for (Booking b : bundle.getBookings()) {
            ArrayList<Booking> bookings = bookingRepo.findAllByAd(b.getAd());
            for (Booking b1 : bookings) {
                if (reservedBookings.containsKey(b1.getId()))
                    continue;
                b1.setState(RequestState.CANCELED);
                bookingRepo.save(b1);
            }
        }

        return true;
    }

    public boolean acceptBookingRequest(Long id, Principal user) throws JSONException {
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(user.getName());
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent())
            return false;

        booking.get().setState(RequestState.RESERVED);
        bookingRepo.save(booking.get());

        carsAdsProxy.deactivateAd(booking.get().getAd(), user.getName() + ";MASTER");

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (booking.get().getState().equals(RequestState.RESERVED)) {
                            booking.get().setState(RequestState.CANCELED);
                            bookingRepo.save(booking.get());
                            cancel();
                        }
                    }
                },
                12 * 60 * 60 * 1000
        );


        return true;
    }

    public boolean cancelBookingRequest(Long id, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Long userId = userIdResponse.getBody();

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() == RequestState.PAID || booking.get().getState() == RequestState.ENDED || userId != booking.get().getLoaner())
            return false;

        booking.get().setState(RequestState.CANCELED);
        bookingRepo.save(booking.get());


        return true;
    }

    public boolean rejectBookingRequest(Long id, Principal user) throws JSONException {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(user.getName());
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() != RequestState.PENDING)
            return false;

        JSONArray array = new JSONArray();
        array.put(booking.get().getAd());
        JSONObject object = new JSONObject();
        object.put("array", array);

        Boolean check = carsAdsProxy.checking(object, user.getName() + ";MASTER");

        if (check == null || !check)
            return false;

        booking.get().setState(RequestState.CANCELED);
        bookingRepo.save(booking.get());


        return true;
    }

    public ArrayList<BookingDTO> getAllBookingRequests(String email) {
        ArrayList<BookingDTO> bookingDTOS = new ArrayList<>();
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return bookingDTOS;

        Long userId = userIdResponse.getBody();

        ArrayList<Booking> bookings = bookingRepo.findAllByLoaner(userId);
        for (Booking booking : bookings) {
            BookingDTO dto = new BookingDTO(booking);
            bookingDTOS.add(dto);
        }

        return bookingDTOS;

    }

    public Set<BookingDTO> getAllBookingRequestsFromOthers(String email) {
        Set<BookingDTO> bookingDTOS = new HashSet<>();
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null) // provera da li postoji
            return bookingDTOS;

        ResponseEntity<List<AdClientDTO>> adsResponse = carsAdsProxy.getClientAds(email + ";MASTER");
        if (adsResponse == null || adsResponse.getStatusCode().isError() || adsResponse.getBody() == null)
            return bookingDTOS;

        adsResponse.getBody().forEach(ad -> {
            bookingRepo.findAllByAd(ad.getAdId()).forEach(book -> {
                bookingDTOS.add(new BookingDTO(book));
            });
        });

        return bookingDTOS;
    }

    public boolean checkingBookingRequests(String jsonObject, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Long userId = userIdResponse.getBody();

        String adsIds = jsonObject;
        String[] str = adsIds.split(";");
        for (String s : str) {
            for (Booking b : bookingRepo.findAllByAd(Long.parseLong(s))) {
                if (!b.getState().equals(RequestState.CANCELED) && !b.getState().equals(RequestState.PENDING))
                    return false;
            }
        }


        return true;
    }

    public boolean deleteCarsBookings(String id, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        String[] str = id.split(";");
        for (String s : str) {
            for (Booking b : bookingRepo.findAllByAd(Long.parseLong(s))) {
                b.setState(RequestState.CANCELED);
                bookingRepo.save(b);
            }
        }


        return true;
    }

    public BookingDTO getBooking(Long id, String email) {
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return new BookingDTO();

        Optional<Booking> booking = bookingRepo.findById(id);
        if (booking.isPresent())
            return new BookingDTO(booking.get());

        return new BookingDTO();
    }
}
