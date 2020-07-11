package carRent.service;

import carRent.model.Booking;
import carRent.model.Bundle;
import carRent.model.RequestState;
import carRent.model.dto.*;
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

    public boolean createBookingRequest(BundleDTO bundleDTO, String email) {


        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        List<Booking> bookingInBundle = new ArrayList<>();

        Long userId = userIdResponse.getBody();

        Bundle bundle = new Bundle();
        bundle.setLoaner(userId);
        if (bundleDTO.getBooks().size() > 1) {
            for (BookDTO bookDTO : bundleDTO.getBooks()) {

                if (bookDTO == null || bookDTO.getAdId() == null || bookDTO.getEndDate() == null || bookDTO.getStartDate() == null || bookDTO.getPlace() == null)
                    return false;

                if (bookDTO.getPlace().equals(""))
                    return false;

                if (bookDTO.getStartDate().isAfter(bookDTO.getEndDate()))
                    return false;

                // provera da li ad postoji odnosno da li je aktivan
                ResponseEntity<Boolean> check = carsAdsProxy.getStatus(bookDTO.getAdId(), email + ";MASTER");
                if (check == null || check.getBody() == null || !check.getBody())
                    return false;


                // provera da client nmz sam svoje da rezervise
                ResponseEntity<Long> ownerId = carsAdsProxy.getOwnerById(bookDTO.getAdId(), email + ";MASTER");
                if (ownerId == null || ownerId.getBody() == null || ownerId.getBody() == userId)
                    return false;

                Booking booking = new Booking(bookDTO.getStartDate(), bookDTO.getEndDate(), RequestState.PENDING, bookDTO.getPlace(), LocalDateTime.now(), bookDTO.getAdId(), userId);
                bundle.getBookings().add(booking);

                bookingRepo.save(booking);
                bookingInBundle.add(booking);

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
            for (Booking b : bookingInBundle) {
                b.setBundle(bundle);
                bookingRepo.save(b);
            }
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

    public HashMap<Long, Booking> reserveBookingRequest(BundleDTO bundleDTO, String email) {

        HashMap<Long, Booking> reservedBookings = new HashMap<>();

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return new HashMap<>();


        Bundle bundle = new Bundle();
        bundle.setLoaner((long) -1);
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
                if (b1.getState().equals(RequestState.PENDING)) {
                    b1.setState(RequestState.CANCELED);
                    bookingRepo.save(b1);
                }
            }
        }

        carsAdsProxy.deactivateAd(adId, email + ";MASTER");


        return reservedBookings;
    }

    public boolean acceptBookingRequest(Long id, String user) {
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(user, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Booking> booking = bookingRepo.findByIdAndBundleId(id, null);
        if (!booking.isPresent())
            return false;

        booking.get().setState(RequestState.PAID);
        bookingRepo.save(booking.get());

        List<Booking> bookings = bookingRepo.findAllByAdAndBundleId(booking.get().getAd(), null);

        for (Booking b : bookings) {
            if (booking.get().getId() != b.getId() && booking.get().getState().equals(RequestState.PENDING)) {
                b.setState(RequestState.CANCELED);
                bookingRepo.save(b);
            }

        }
        carsAdsProxy.deactivateAd(booking.get().getAd(), user + ";MASTER");

        return true;
    }

    public boolean cancelBookingRequest(Long id, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Long userId = userIdResponse.getBody();

        Optional<Booking> booking = bookingRepo.findByIdAndBundleId(id, null);
        if (!booking.isPresent() || booking.get().getState() == RequestState.PAID || booking.get().getState() == RequestState.ENDED || userId != booking.get().getLoaner())
            return false;

        booking.get().setState(RequestState.CANCELED);
        bookingRepo.save(booking.get());


        return true;
    }

    public boolean rejectBookingRequest(Long id, String user) throws JSONException {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(user, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Booking> booking = bookingRepo.findByIdAndBundleId(id, null);
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


    public ArrayList<BookingDTO> getAllSentBookingRequests(String email) {
        ArrayList<BookingDTO> bookingDTOS = new ArrayList<>();
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return bookingDTOS;

        Long userId = userIdResponse.getBody();

        ArrayList<Booking> bookings = bookingRepo.findAllByLoanerAndBundleId(userId, null);
        for (Booking booking : bookings) {
            ResponseEntity<AdClientDTO> ad = carsAdsProxy.getAd(booking.getAd(), email + ";MASTER");
            if (ad != null && ad.getBody() != null) {
                BookingDTO dto = new BookingDTO(booking, ad.getBody().getAdvertiser());
                bookingDTOS.add(dto);
            }


        }

        return bookingDTOS;

    }

    public Set<BookingDTO> getAllReceivedBookingRequests(String email) {
        Set<BookingDTO> bookingDTOS = new HashSet<>();
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null) // provera da li postoji
            return bookingDTOS;

        ResponseEntity<List<AdClientDTO>> adsResponse = carsAdsProxy.getClientAds(email + ";MASTER");
        if (adsResponse == null || adsResponse.getStatusCode().isError() || adsResponse.getBody() == null)
            return bookingDTOS;

        for (AdClientDTO ad : adsResponse.getBody()) {
            List<Booking> bookings = bookingRepo.findAllByAdAndBundleId(ad.getAdId(), null);
            for (Booking book : bookings)
                bookingDTOS.add(new BookingDTO(book));
        }

        return bookingDTOS;
    }

    public boolean checkingBookingRequests(String jsonObject, String email) throws JSONException {

        if (jsonObject.equals(""))
            return true;

        JSONObject object = new JSONObject(jsonObject);

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        String adsIds = (String) object.get("ads");
        if (adsIds.equals("NONE"))
            return true;

        String[] str = adsIds.split(";");
        for (String s : str) {
            for (Booking b : bookingRepo.findAllByAdAndBundleId(Long.parseLong(s), null)) {
                if (!b.getState().equals(RequestState.CANCELED) && !b.getState().equals(RequestState.PENDING))
                    return false;
            }
        }


        return true;
    }

    public boolean deleteCarsBookings(String id, String email) throws JSONException {

        if (id.equals(""))
            return true;

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        JSONObject object = new JSONObject(id);
        String idd = (String) object.get("ads");
        if (idd.equals("NONE"))
            return true;

        String[] str = idd.split(";");
        for (String s : str) {
            for (Booking b : bookingRepo.findAllByAd(Long.parseLong(s))) {
                b.setState(RequestState.CANCELED);
                bookingRepo.save(b);
            }
        }


        return true;
    }

    public BookingDTO getBooking(Long id, String email) {
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return new BookingDTO();

        Optional<Booking> booking = bookingRepo.findByIdAndBundleId(id, null);
        if (booking.isPresent())
            return new BookingDTO(booking.get());

        return new BookingDTO();
    }

    public Boolean checkStates(Long id, String name) {

        List<Booking> bookings = bookingRepo.findAllByAd(id);
        for (Booking b : bookings)
            if (b.getState().equals(RequestState.PAID) || b.getState().equals(RequestState.ENDED))
                return false;

        return true;
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

    public boolean endBookingRequest(Long id, String user) {
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(user, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Booking> booking = bookingRepo.findByIdAndBundleId(id, null);
        // dodao sam da prolazi i kad je vec ended jer se ovo poziva i kad se pravi report pa da ne pravi greske (kad je vec ended, ovo nista ne menja, ne smeta)
        if (!booking.isPresent() || booking.get().getState() != RequestState.PAID || booking.get().getState() != RequestState.ENDED)
            return false;

        booking.get().setState(RequestState.ENDED);
        bookingRepo.save(booking.get());

        return true;
    }

    // soap bookings
    public Set<SoapBookingDTO> getAllReceivedBookingRequestsSoap(String email) {
        Set<SoapBookingDTO> bookingDTOS = new HashSet<>();
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, "NONE;MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null) // provera da li postoji
            return bookingDTOS;

        ResponseEntity<List<AdClientDTO>> adsResponse = carsAdsProxy.getClientAds(email + ";MASTER");
        if (adsResponse == null || adsResponse.getStatusCode().isError() || adsResponse.getBody() == null)
            return bookingDTOS;

        for (AdClientDTO ad : adsResponse.getBody()) {
            System.out.println("Ad id: " + ad.getAdId());
            List<Booking> bookings = bookingRepo.findAllByAdAndBundleId(ad.getAdId(), null);
            for (Booking book : bookings) {
                ResponseEntity<String> loaner = userProxy.getUserEmail(book.getLoaner(), "NONE;MASTER");
                if (loaner != null && loaner.getBody() != null && !loaner.getBody().equals(""))
                    bookingDTOS.add(new SoapBookingDTO(book, loaner.getBody()));
            }
        }

        return bookingDTOS;
    }

    // MAPPING
    public BundleDTO mappingDto(BundleDetails bundleDetails) {
        return new BundleDTO(bundleDetails);
    }


    public Set<BookingDetails> mappingDtoArray(Set<SoapBookingDTO> bookings) throws DatatypeConfigurationException {
        Set<BookingDetails> bookingDetails = new HashSet<>();
        List<SoapBookingDTO> bookingDTOList = new ArrayList<>();
        bookingDTOList.addAll(bookings);
        for (SoapBookingDTO bookingDTO : bookingDTOList) {
            BookingDetails bookingDetail = new BookingDetails();
            bookingDetail.setAd(bookingDTO.getAd());
            bookingDetail.setAdvertiser(bookingDTO.getLoaner());
            bookingDetail.setId(bookingDTO.getId());
            bookingDetail.setPlace(bookingDTO.getPlace());

            bookingDetail.setCreated(DatatypeFactory.newInstance().newXMLGregorianCalendar(bookingDTO.getCreated().toLocalDate().toString()));
            bookingDetail.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(bookingDTO.getStartDate().toLocalDate().toString()));
            bookingDetail.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(bookingDTO.getEndDate().toLocalDate().toString()));

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


}
