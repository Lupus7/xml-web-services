package carRent.service;

import carRent.model.Booking;
import carRent.model.Bundle;
import carRent.model.RequestState;
import carRent.model.dto.BookDTO;
import carRent.model.dto.BookingDTO;
import carRent.model.dto.BundleDTO;
import carRent.repository.BookingRepository;
import carRent.repository.BundleRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private CartService cartService;

    @Autowired
    private BundleRepository bundleRepository;

    public boolean createBookingRequest(BundleDTO bundleDTO, String email) throws JSONException {

        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        Long userId = userIdRes.getBody();

        Bundle bundle = new Bundle();

        if (bundleDTO.getBooks().size() > 1) {
            for (BookDTO bookDTO : bundleDTO.getBooks()) {

                if (bookDTO == null || bookDTO.getAdId() == null || bookDTO.getEndDate() == null || bookDTO.getStartDate() == null || bookDTO.getPlace() == null)
                    return false;

                if (bookDTO.getPlace().equals(""))
                    return false;

                if (bookDTO.getStartDate().isAfter(bookDTO.getEndDate()))
                    return false;


                ResponseEntity<Boolean> check = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/ad/check/" + bookDTO.getAdId(), HttpMethod.GET, entity,
                        Boolean.class, new Object());

                if (check == null || check.getBody() == null || !check.getBody())
                    return false;

                // provera da client nmz sam svoje da rezervise
                ResponseEntity<Long> ownerId = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/ad/owner/" + bookDTO.getAdId(), HttpMethod.GET, entity,
                        Long.class, new Object());


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

            ResponseEntity<Boolean> check = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/ad/check/" + bundleDTO.getBooks().get(0).getAdId(), HttpMethod.GET, entity,
                    Boolean.class, new Object());

            if (check == null || check.getBody() == null || !check.getBody())
                return false;

            // provera da client nmz sam svoje da rezervise
            ResponseEntity<Long> ownerId = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/ad/owner/" + bundleDTO.getBooks().get(0).getAdId(), HttpMethod.GET, entity,
                    Long.class, new Object());

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
        // provera da li user sa name postoji
        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        Long userId = userIdRes.getBody();

        if (bundleDTO.getLoaner().equals(""))
            return false;

        ResponseEntity<Long> loanerIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + bundleDTO.getLoaner(), HttpMethod.GET, entity, Long.class, new Object());
        if (loanerIdRes == null || loanerIdRes.getBody() == null)
            return false;
        Long loanerId = loanerIdRes.getBody();

        Bundle bundle = new Bundle();
        if (bundleDTO.getBooks().size() > 1) {
            for (BookDTO bookDTO : bundleDTO.getBooks()) {

                if (bookDTO == null || bookDTO.getAdId() == null || bookDTO.getEndDate() == null || bookDTO.getStartDate() == null || bookDTO.getPlace() == null)
                    return false;

                if (bookDTO.getPlace().equals(""))
                    return false;

                if (bookDTO.getStartDate().isAfter(bookDTO.getEndDate()))
                    return false;

                ResponseEntity<Boolean> check = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/ad/check/" + bookDTO.getAdId(),
                        HttpMethod.GET, entity, Boolean.class, new Object());

                if (check == null || check.getBody() == null || !check.getBody())
                    return false;

                // provera da client nmz sam svoje da rezervise
                ResponseEntity<Long> ownerId = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/ad/owner/" + bookDTO.getAdId(), HttpMethod.GET, entity,
                        Long.class, new Object());

                if (ownerId == null || ownerId.getBody() == null || ownerId.getBody().longValue() == userId)
                    return false;

                Booking booking = new Booking(bookDTO.getStartDate(), bookDTO.getEndDate(), RequestState.PAID, bookDTO.getPlace(), LocalDateTime.now(), bookDTO.getAdId(), (long) -1);
                bundle.getBookings().add(booking);
                bookingRepo.save(booking);

            }

            bundleRepository.save(bundle);
        } else {

            if (bundleDTO.getBooks().get(0) == null || bundleDTO.getBooks().get(0).getAdId() == null || bundleDTO.getBooks().get(0).getEndDate() == null || bundleDTO.getBooks().get(0).getStartDate() == null || bundleDTO.getBooks().get(0).getPlace() == null)
                return false;

            if (bundleDTO.getBooks().get(0).getPlace().equals(""))
                return false;

            if (bundleDTO.getBooks().get(0).getStartDate().isAfter(bundleDTO.getBooks().get(0).getEndDate()))
                return false;

            ResponseEntity<Boolean> check = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/ad/check/" + bundleDTO.getBooks().get(0).getAdId(),
                    HttpMethod.GET, entity, Boolean.class, new Object());

            if (check == null || check.getBody() == null || !check.getBody())
                return false;

            // provera da client nmz sam svoje da rezervise
            ResponseEntity<Long> ownerId = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/ad/owner/" + bundleDTO.getBooks().get(0).getAdId(), HttpMethod.GET, entity,
                    Long.class, new Object());

            if (ownerId == null || ownerId.getBody() == null || ownerId.getBody().longValue() == userId)
                return false;

            Booking booking = new Booking(bundleDTO.getBooks().get(0).getStartDate(), bundleDTO.getBooks().get(0).getEndDate(), RequestState.PAID, bundleDTO.getBooks().get(0).getPlace(), LocalDateTime.now(), bundleDTO.getBooks().get(0).getAdId(), (long) -1);
            bundle.getBookings().add(booking);
            bookingRepo.save(booking);

        }

        //canceluj sve ostale bookinge koju su vezani za taj ad

        for (Booking b : bundle.getBookings()) {
            ArrayList<Booking> bookings = bookingRepo.findAllByAd(b.getAd());
            for (Booking b1 : bookings) {
                b1.setState(RequestState.CANCELED);
                bookingRepo.save(b1);
            }
        }

        return true;
    }

    public boolean acceptBookingRequest(Long id, String email) throws JSONException {
        // provera da li user sa name postoji
        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        Long userId = userIdRes.getBody();

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent())
            return false;

        booking.get().setState(RequestState.RESERVED);
        bookingRepo.save(booking.get());

        Map<String, Long> params = new HashMap<String, Long>();
        params.put("id", booking.get().getAd());
        new RestTemplate().delete("http://" + carsAdsServiceIp + ":8080/api/ad/deactivate/{id}", params);


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

        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();

        // provera da li user sa name postoji
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        Long userId = userIdRes.getBody();

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() != RequestState.RESERVED || userId != booking.get().getLoaner())
            return false;

        booking.get().setState(RequestState.CANCELED);
        bookingRepo.save(booking.get());


        return true;
    }

    public boolean rejectBookingRequest(Long id, String email) throws JSONException {

        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();

        // provera da li user sa name postoji, provera da li je ad userov
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        Long userId = userIdRes.getBody();

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() != RequestState.PENDING)
            return false;

        //provera da li je ad ili ads userov
        JSONArray array = new JSONArray();
        array.put(booking.get().getAd());
        JSONObject object = new JSONObject();
        object.put("array", array);

        headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");

        entity = new HttpEntity<>(object, headers);

        Boolean check = new RestTemplate().postForObject("http://" + carsAdsServiceIp + ":8080/api/ad/check", entity, Boolean.class);

        if (check == null || !check)
            return false;

        booking.get().setState(RequestState.CANCELED);
        bookingRepo.save(booking.get());


        return true;
    }

    public ArrayList<BookingDTO> getAllBookingRequests(String email) {
        // provera da li user sa name postoji
        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();

        ArrayList<BookingDTO> bookingDTOS = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return bookingDTOS;

        Long userId = userIdRes.getBody();

        ArrayList<Booking> bookings = bookingRepo.findAllByLoaner(userId);
        for (Booking booking : bookings) {
            BookingDTO dto = new BookingDTO(booking);
            bookingDTOS.add(dto);
        }

        return bookingDTOS;

    }


    public boolean checkingBookingRequests(JSONObject jsonObject, String email) throws JSONException {

        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        String adsIds = jsonObject.getString("array");
        String[] str = adsIds.split(";");
        for (String s : str) {
            for (Booking b : bookingRepo.findAllByAd(Long.parseLong("s"))) {
                if (!b.getState().equals(RequestState.CANCELED) && !b.getState().equals(RequestState.PENDING))
                    return false;
            }
        }


        return true;
    }

    public boolean deleteCarsBookings(String id, String email) {

        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        String[] str = id.split(";");
        for (String s : str) {
            for (Booking b : bookingRepo.findAllByAd(Long.parseLong("s"))) {
                b.setState(RequestState.CANCELED);
                bookingRepo.save(b);
            }
        }


        return true;
    }

    public BookingDTO getBooking(Long id, String email) {
        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return new BookingDTO();

        Optional<Booking> booking = bookingRepo.findById(id);
        if (booking.isPresent())
            return new BookingDTO(booking.get());

        return new BookingDTO();
    }
}
