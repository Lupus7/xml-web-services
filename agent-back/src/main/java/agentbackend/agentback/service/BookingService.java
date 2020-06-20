package agentbackend.agentback.service;

import agentbackend.agentback.controller.dto.BookDTO;
import agentbackend.agentback.controller.dto.BookingDTO;
import agentbackend.agentback.model.Ad;
import agentbackend.agentback.model.Booking;
import agentbackend.agentback.model.RequestState;
import agentbackend.agentback.model.User;
import agentbackend.agentback.repository.AdRepository;
import agentbackend.agentback.repository.BookingRepository;
import agentbackend.agentback.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdRepository adRepository;

    public boolean createBookingRequest(BookDTO bookDTO, String email) throws JSONException {


        User user  = userRepository.findByEmail(email);
        if (user == null )
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

                // provera da client nmz sam svoje da rezervise
        ArrayList<Ad> ads = adRepository.findAllByOwnerId(user.getId());
        for(Ad add: ads){
            if(add.getId() == ad.get().getId()){
                return  false;
            }
        }

        Booking booking = new Booking(bookDTO.getStartDate(), bookDTO.getEndDate(), RequestState.PENDING, bookDTO.getPlace(), LocalDateTime.now(), bookDTO.getAdId(), user.getId());
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

    public boolean reserveBookingRequest(BookDTO bookDTO , String email) throws JSONException {

        User user  = userRepository.findByEmail(email);
        if (user == null )
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
        //DA NE MOZE DA ZAKAZE SVOJ
        ArrayList<Ad> ads = adRepository.findAllByOwnerId(user.getId());
        for(Ad add: ads){
            if(add.getId() == ad.get().getId()){
                return  false;
            }
        }
        // OTKAZI PRVO SVE ZAHTEVE
        ArrayList<Booking> bookings = bookingRepo.findAllByAd(ad.get().getId());
        for (Booking b1 : bookings) {
            b1.setState(RequestState.CANCELED);
            bookingRepo.save(b1);
        }
        //SACUVAJ OVAJ ZAHTEV SA STATUSOM PAID
        Booking booking = new Booking(bookDTO.getStartDate(), bookDTO.getEndDate(), RequestState.PAID, bookDTO.getPlace(), LocalDateTime.now(), bookDTO.getAdId(), (long)-1);
        bookingRepo.save(booking);

        return true;
    }

    public boolean acceptBookingRequest(Long id, Principal user) throws JSONException {
        User userr  = userRepository.findByEmail(user.getName());
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

        return true;
    }

    public boolean cancelBookingRequest(Long id, String email) {

        User user  = userRepository.findByEmail(email);
        if (user == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() != RequestState.PAID || booking.get().getState() != RequestState.ENDED || user.getId() != booking.get().getLoaner())
            return false;

        booking.get().setState(RequestState.CANCELED);
        bookingRepo.save(booking.get());

        return true;
    }

    public boolean rejectBookingRequest(Long id, Principal user) throws JSONException {

        User userr  = userRepository.findByEmail(user.getName());
        if (userr == null)
            return false;

        Optional<Booking> booking = bookingRepo.findById(id);
        if (!booking.isPresent() || booking.get().getState() != RequestState.PENDING)
            return false;

        Ad ad = adRepository.getOne(booking.get().getAd());
        if(ad == null){
            return false;
        }

        if(ad.getOwnerId() == userr.getId()){
            return  false;
        }

        booking.get().setState(RequestState.CANCELED);
        bookingRepo.save(booking.get());

        return true;
    }

    public ArrayList<BookingDTO> getAllBookingRequests(String email) {
        ArrayList<BookingDTO> bookingDTOS = new ArrayList<>();
        User user  = userRepository.findByEmail(email);
        if (user == null)
            return null;

        Long userId = user.getId();

        ArrayList<Booking> bookings = bookingRepo.findAllByLoaner(userId);
        for (Booking booking : bookings) {
            BookingDTO dto = new BookingDTO(booking);
            bookingDTOS.add(dto);
        }

        return bookingDTOS;

    }


    public boolean checkingBookingRequests(String jsonObject, String email) {

        User user  = userRepository.findByEmail(email);
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


        return true;
    }

    public boolean deleteCarsBookings(String id, String email) {

        User user  = userRepository.findByEmail(email);
        if (user == null)
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
        User user  = userRepository.findByEmail(email);
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
