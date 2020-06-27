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
import com.car_rent.agent_api.BookingDetails;
import com.car_rent.agent_api.BundleDetails;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
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

                // provera da li ad postoji odnosno da li je aktivan
                ResponseEntity<Boolean> check = carsAdsProxy.getStatus(bundleDTO.getBooks().get(0).getAdId(), email + ";MASTER");
                if (check == null || check.getBody() == null || !check.getBody())
                    return false;


                // provera da client nmz sam svoje da rezervise
                ResponseEntity<Long> ownerId = carsAdsProxy.getOwnerById(bookDTO.getAdId(), email + ";MASTER");
                if (ownerId == null || ownerId.getBody() == null || ownerId.getBody() == userId)
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

            // provera da li ad postoji odnosno da li je aktivan
            ResponseEntity<Boolean> check = carsAdsProxy.getStatus(bundleDTO.getBooks().get(0).getAdId(), email + ";MASTER");
            if (check == null || check.getBody() == null || !check.getBody())
                return false;


            // provera da client nmz sam svoje da rezervise
            ResponseEntity<Long> ownerId = carsAdsProxy.getOwnerById(bundleDTO.getBooks().get(0).getAdId(), email + ";MASTER");
            if (ownerId == null || ownerId.getBody() == null || ownerId.getBody() == userId)
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

    public HashMap<Long, Booking> reserveBookingRequest(BundleDTO bundleDTO, String email) throws JSONException {

        HashMap<Long, Booking> reservedBookings = new HashMap<>();

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return new HashMap<>();


        Bundle bundle = new Bundle();
        if (bundleDTO.getBooks().size() > 1) {
            for (BookDTO bookDTO : bundleDTO.getBooks()) {

                if (bookDTO == null || bookDTO.getAdId() == null || bookDTO.getEndDate() == null || bookDTO.getStartDate() == null || bookDTO.getPlace() == null)
                    return new HashMap<>();

                if (bookDTO.getPlace().equals(""))
                    return new HashMap<>();

                if (bookDTO.getStartDate().isAfter(bookDTO.getEndDate()))
                    return new HashMap<>();

                // provera da li ad postoji odnosno da li je aktivan
                ResponseEntity<Boolean> check = carsAdsProxy.getStatus(bundleDTO.getBooks().get(0).getAdId(), email + ";MASTER");
                if (check == null || check.getBody() == null || !check.getBody())
                    return new HashMap<>();


                Booking booking = new Booking(bookDTO.getStartDate(), bookDTO.getEndDate(), RequestState.PAID, bookDTO.getPlace(), LocalDateTime.now(), bookDTO.getAdId(), (long) -1);
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

            ResponseEntity<Boolean> check = carsAdsProxy.getStatus(bundleDTO.getBooks().get(0).getAdId(), email + ";MASTER");
            if (check == null || check.getBody() == null || !check.getBody())
                return new HashMap<>();

            Booking booking = new Booking(bundleDTO.getBooks().get(0).getStartDate(), bundleDTO.getBooks().get(0).getEndDate(), RequestState.PAID, bundleDTO.getBooks().get(0).getPlace(), LocalDateTime.now(), bundleDTO.getBooks().get(0).getAdId(), (long) -1);
            bookingRepo.save(booking);
            reservedBookings.put(booking.getId(), booking);

        }

        //canceluj sve ostale bookinge koju su vezani za taj ad
        Long adId = null;
        for (Booking b : reservedBookings.values()) {
            adId = b.getAd();
            ArrayList<Booking> bookings = bookingRepo.findAllByAd(b.getAd());
            for (Booking b1 : bookings) {
                if (reservedBookings.containsKey(b1.getId()))
                    continue;
                b1.setState(RequestState.CANCELED);
                bookingRepo.save(b1);
            }
        }

        carsAdsProxy.deactivateAd(adId, email + ";MASTER");


        return reservedBookings;
    }

    public boolean acceptBookingRequest(Long id, String user) {
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(user);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent())
            return false;

        booking.get().setState(RequestState.PAID);
        bookingRepo.save(booking.get());

        List<Booking> bookings = bookingRepo.findAllByAd(booking.get().getAd());

        for (Booking b : bookings) {
            if (booking.get().getId() != b.getId()) {
                b.setState(RequestState.CANCELED);
                bookingRepo.save(b);
            }

        }
        carsAdsProxy.deactivateAd(booking.get().getAd(), user + ";MASTER");

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

    public boolean rejectBookingRequest(Long id, String user) throws JSONException {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(user);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() != RequestState.PENDING)
            return false;

        JSONArray array = new JSONArray();
        array.put(booking.get().getAd());
        JSONObject object = new JSONObject();
        object.put("array", array);

        System.out.println(object);

        Boolean check = carsAdsProxy.checking(object.toString(), user + ";MASTER");

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
            ResponseEntity<AdClientDTO> ad = carsAdsProxy.getAd(booking.getAd(), email + ";MASTER");
            if (ad != null && ad.getBody() != null) {
                BookingDTO dto = new BookingDTO(booking, ad.getBody().getAdvertiser());
                bookingDTOS.add(dto);
            }


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

    // MAPPING
    public BundleDTO mappingDto(BundleDetails bundleDetails) {
        return new BundleDTO(bundleDetails);
    }


    public List<BookingDetails> mappingDtoList(ArrayList<BookingDTO> bookings) throws DatatypeConfigurationException {
        List<BookingDetails> bookingDetails = new ArrayList<>();
        for (BookingDTO bookingDTO : bookings) {
            BookingDetails bookingDetail = new BookingDetails();
            bookingDetail.setAd(bookingDTO.getAd());
            bookingDetail.setAdvertiser(bookingDTO.getAdvertiser());
            bookingDetail.setCreated(DatatypeFactory.newInstance().newXMLGregorianCalendar(bookingDTO.getCreated().toString()));
            bookingDetail.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(bookingDTO.getStartDate().toString()));
            bookingDetail.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(bookingDTO.getEndDate().toString()));
            bookingDetail.setId(bookingDTO.getId());
            bookingDetail.setPlace(bookingDTO.getPlace());

            if (bookingDTO.getState().equals(RequestState.PENDING))
                bookingDetail.setState(0);
            else if (bookingDTO.getState().equals(RequestState.PAID))
                bookingDetail.setState(1);
            else if (bookingDTO.getState().equals(RequestState.CANCELED))
                bookingDetail.setState(2);
            else if (bookingDTO.getState().equals(RequestState.ENDED))
                bookingDetail.setState(3);

            bookingDetails.add(bookingDetail);
        }

        return bookingDetails;
    }


    public Set<BookingDetails> mappingDtoArray(Set<BookingDTO> bookings) throws DatatypeConfigurationException {

        Set<BookingDetails> bookingDetails = new HashSet<>();
        for (BookingDTO bookingDTO : new ArrayList<>(bookings)) {
            BookingDetails bookingDetail = new BookingDetails();
            bookingDetail.setAd(bookingDTO.getAd());
            bookingDetail.setAdvertiser(bookingDTO.getAdvertiser());
            bookingDetail.setId(bookingDTO.getId());
            bookingDetail.setPlace(bookingDTO.getPlace());
            bookingDetail.setCreated(DatatypeFactory.newInstance().newXMLGregorianCalendar(bookingDTO.getCreated().toString()));
            bookingDetail.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(bookingDTO.getStartDate().toString()));
            bookingDetail.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(bookingDTO.getEndDate().toString()));

            if (bookingDTO.getState().equals(RequestState.PENDING))
                bookingDetail.setState(0);
            else if (bookingDTO.getState().equals(RequestState.PAID))
                bookingDetail.setState(1);
            else if (bookingDTO.getState().equals(RequestState.CANCELED))
                bookingDetail.setState(2);
            else if (bookingDTO.getState().equals(RequestState.ENDED))
                bookingDetail.setState(3);

            bookingDetails.add(bookingDetail);
        }

        return bookingDetails;
    }

    public BookingDetails mappingBookingDTO(BookingDTO booking) {

        BookingDetails bookingDetails = new BookingDetails();

        return bookingDetails;

    }

    public boolean endBookingRequest(Long id, String user) {
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(user);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent())
            return false;

        booking.get().setState(RequestState.ENDED);
        bookingRepo.save(booking.get());

        return true;
    }


}
