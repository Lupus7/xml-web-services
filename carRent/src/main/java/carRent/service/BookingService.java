package carRent.service;

import carRent.model.Booking;
import carRent.model.RequestState;
import carRent.model.User;
import carRent.model.dto.AdDTO;
import carRent.model.dto.BookingDTO;
import carRent.repository.BookingRepository;
import carRent.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private UserRepository userRepo;

    public boolean createBookingRequest(String json, String name) throws JSONException {

        // provera da li user sa name postoji
        User user = userRepo.findByEmail(name);
        if (user == null)
            return false;

        JSONObject obj = new JSONObject(json);
        if (obj == null || obj.get("adId") == null || obj.get("startDate") == null || obj.get("endDate") == null
                || obj.get("place") == null)
            return false;

        if (obj.get("adId").equals("") || obj.get("startDate").equals("") || obj.get("endDate").equals("")
                || obj.get("place").equals(""))
            return false;

        AdDTO ad = new RestTemplate().getForObject("http://localhost:8080/cars/api/ads/"+obj.getLong("adId"),
                AdDTO.class);

        if (ad == null)
            return false;

        // proveri da li konvertuje datume kako treba
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse((String) obj.get("startDate"), formatter);
        LocalDateTime endDate = LocalDateTime.parse((String) obj.get("endDate"), formatter);

        Booking booking = new Booking(startDate, endDate, RequestState.PENDING, obj.getString("place"), LocalDateTime.now(), ad.getId(), user.getId());
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

        return true;
    }

    public boolean acceptBookingRequest(Long id, String name) throws JSONException {

        // provera da li user sa name postoji
        User user = userRepo.findByEmail(name);
        if (user == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent())
            return false;

        booking.get().setState(RequestState.RESERVED);
        bookingRepo.save(booking.get());

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

    public boolean cancelBookingRequest(Long id, String name) {

        // provera da li user sa name postoji
        User user = userRepo.findByEmail(name);
        if (user == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() != RequestState.RESERVED)
            return false;

        booking.get().setState(RequestState.CANCELED);
        bookingRepo.save(booking.get());


        return true;
    }

    public boolean rejectBookingRequest(Long id, String name) {

        // provera da li user sa name postoji
        User user = userRepo.findByEmail(name);
        if (user == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() != RequestState.PENDING)
            return false;

        booking.get().setState(RequestState.CANCELED);
        bookingRepo.save(booking.get());


        return true;
    }

    public ArrayList<BookingDTO> getAllBookingRequests(String name) {
        // provera da li user sa name postoji
        ArrayList<BookingDTO> bookingDTOS = new ArrayList<>();
        User user = userRepo.findByEmail(name);
        if (user == null)
            return bookingDTOS;

        ArrayList<Booking> bookings = bookingRepo.findAllByLoaner(user.getId());
        for (Booking booking : bookings) {
            BookingDTO dto = new BookingDTO(booking);
            bookingDTOS.add(dto);
        }

        return bookingDTOS;

    }


}
