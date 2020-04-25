package carRent.service;

import carRent.model.Ad;
import carRent.model.Booking;
import carRent.model.RequestState;
import carRent.model.User;
import carRent.repository.AdRepository;
import carRent.repository.BookingRepository;
import carRent.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private AdRepository adRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BookingRepository bookingRepo;

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

        Optional<Ad> ad = adRepo.findById(obj.getLong("adId"));
        if (!ad.isPresent())
            return false;

        // proveri da li konvertuje datume kako treba
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse((String) obj.get("startDate"), formatter);
        LocalDateTime endDate = LocalDateTime.parse((String) obj.get("endDate"), formatter);

        Booking booking = new Booking(startDate, endDate, RequestState.PENDING, obj.getString("place"), LocalDateTime.now(), ad.get().getCar(), user);
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
                24* 60 * 60 * 1000
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
}
