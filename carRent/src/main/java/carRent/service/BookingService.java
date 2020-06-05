package carRent.service;

import carRent.model.Booking;
import carRent.model.Bundle;
import carRent.model.RequestState;
import carRent.model.dto.BookDTO;
import carRent.model.dto.BookingDTO;
import carRent.model.dto.BundleDTO;
import carRent.repository.BookingRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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

    public boolean createBookingRequest(BundleDTO bundleDTO, String email) throws JSONException {

        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();

        // provera da li user sa name postoji
        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null)
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

                Boolean check = new RestTemplate().getForObject("http://" + carsAdsServiceIp + ":8080/ad/check/" + bookDTO.getAdId(),
                        Boolean.class);

                if (check == null || !check)
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
        } else {
            if (bundleDTO.getBooks().get(0) == null || bundleDTO.getBooks().get(0).getAdId() == null || bundleDTO.getBooks().get(0).getEndDate() == null || bundleDTO.getBooks().get(0).getStartDate() == null || bundleDTO.getBooks().get(0).getPlace() == null)
                return false;

            if (bundleDTO.getBooks().get(0).getPlace().equals(""))
                return false;

            if (bundleDTO.getBooks().get(0).getStartDate().isAfter(bundleDTO.getBooks().get(0).getEndDate()))
                return false;

            Boolean check = new RestTemplate().getForObject("http://" + carsAdsServiceIp + ":8080/ad/check/" + bundleDTO.getBooks().get(0).getAdId(),
                    Boolean.class);

            if (check == null || !check)
                return false;

            Booking booking = new Booking(bundleDTO.getBooks().get(0).getStartDate(), bundleDTO.getBooks().get(0).getEndDate(), RequestState.PENDING, bundleDTO.getBooks().get(0).getPlace(), LocalDateTime.now(), bundleDTO.getBooks().get(0).getAdId(), userId);
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

        return true;
    }

    public boolean reserveBookingRequest(BundleDTO bundleDTO, String email) throws JSONException {
        // provera da li user sa name postoji
        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();

        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null || bundleDTO.getLoaner() == null)
            return false;

        if (bundleDTO.getLoaner().equals(""))
            return false;

        Long loanerId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + bundleDTO.getLoaner(), Long.class);
        if (loanerId == null)
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


                Boolean check = new RestTemplate().getForObject("http://" + carsAdsServiceIp + ":8080/ad/check/" + bookDTO.getAdId(),
                        Boolean.class);

                if (check == null || !check)
                    return false;

                Booking booking = new Booking(bookDTO.getStartDate(), bookDTO.getEndDate(), RequestState.PAID, bookDTO.getPlace(), LocalDateTime.now(), bookDTO.getAdId(), (long) -1);
                bundle.getBookings().add(booking);
                bookingRepo.save(booking);

            }
        } else {

            if (bundleDTO.getBooks().get(0) == null || bundleDTO.getBooks().get(0).getAdId() == null || bundleDTO.getBooks().get(0).getEndDate() == null || bundleDTO.getBooks().get(0).getStartDate() == null || bundleDTO.getBooks().get(0).getPlace() == null)
                return false;

            if (bundleDTO.getBooks().get(0).getPlace().equals(""))
                return false;

            if (bundleDTO.getBooks().get(0).getStartDate().isAfter(bundleDTO.getBooks().get(0).getEndDate()))
                return false;

            Boolean check = new RestTemplate().getForObject("http://" + carsAdsServiceIp + ":8080/ad/check/" + bundleDTO.getBooks().get(0).getAdId(),
                    Boolean.class);

            if (check == null || !check)
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

        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null)
            return false;

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
        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null)
            return false;

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
        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() != RequestState.PENDING)
            return false;

        //provera da li je ad ili ads userov
        JSONArray array = new JSONArray();
        array.put(booking.get().getAd());
        JSONObject object = new JSONObject();
        object.put("array", array);

        Boolean check = new RestTemplate().postForObject("http://" + carsAdsServiceIp + ":8080/api/ad/check", object, Boolean.class);

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
        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null)
            return bookingDTOS;

        ArrayList<Booking> bookings = bookingRepo.findAllByLoaner(userId);
        for (Booking booking : bookings) {
            BookingDTO dto = new BookingDTO(booking);
            bookingDTOS.add(dto);
        }

        return bookingDTOS;

    }


    public boolean checkingBookingRequests(JSONObject jsonObject, String email) throws JSONException {

        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();

        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null)
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

        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null)
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
}
