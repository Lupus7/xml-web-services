package carRent.controller;

import carRent.model.dto.BookingDTO;
import carRent.model.dto.BundleDTO;
import carRent.service.BookingService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    // Kreiranje bookinga
    @PostMapping(value = "/api/booking", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('CREATE_BOOKING')")
    public ResponseEntity<String> createBookingRequest(@RequestBody BundleDTO bundleDTO, Principal user) throws JSONException {

        if (bookingService.createBookingRequest(bundleDTO, user.getName()))
            return ResponseEntity.ok("Booking request successfully created!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Samostalno zauzece
    @PostMapping(value = "/api/booking/reserved", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('RESERVE_BOOKING')")
    public ResponseEntity<String> reserveBookingRequest(@RequestBody BundleDTO bundleDTO, Principal user) throws JSONException {

        if (bookingService.reserveBookingRequest(bundleDTO, user.getName()))
            return ResponseEntity.ok("Booking request successfully created!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Prihvatanje bookinga kod clienta
    @PutMapping(value = "/api/booking/{id}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('ACCEPT_BOOKING')")
    public ResponseEntity<String> acceptBookingRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bookingService.acceptBookingRequest(id, user))
            return ResponseEntity.ok("Booking request reserved!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Otkazivanje booking requesta od strane clienta koje je zatrazio booking
    @DeleteMapping(value = "/api/booking/{id}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('CANCEL_BOOKING')")
    public ResponseEntity<String> cancelBookingRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bookingService.cancelBookingRequest(id, user.getName()))
            return ResponseEntity.ok("Booking request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Booking check
    @PostMapping(value = "/api/booking/checking")
    public ResponseEntity<Boolean> checkingBookingRequests(@RequestBody String jsonObject, Principal user)  {
        return ResponseEntity.ok(bookingService.checkingBookingRequests(jsonObject, user.getName()));

    }

    // Odbijanje booking requesta kod brisanja car-a
    @PostMapping(value = "/api/booking/checking/remove")
    public ResponseEntity<String> deleteCarsBookings(@RequestBody String id, Principal user){

        if (bookingService.deleteCarsBookings(id, user.getName()))
            return ResponseEntity.ok("Booking request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Odbijanje booking requesta od strane vlasnika auta
    @DeleteMapping(value = "/api/booking/reject/{id}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('REJECT_BOOKING')")
    public ResponseEntity<String> rejectBookingRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bookingService.rejectBookingRequest(id, user))
            return ResponseEntity.ok("Booking request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Get all client bookings
    @GetMapping(value = "/api/booking", produces = "application/json")
    @PreAuthorize("hasAuthority('READ_BOOKINGS')")
    public ResponseEntity<ArrayList<BookingDTO>> getAllBookingRequests(Principal user) throws JSONException {

        return ResponseEntity.ok((ArrayList<BookingDTO>) bookingService.getAllBookingRequests(user.getName()));

    }

    @GetMapping(value = "/api/booking/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('READ_BOOKINGS')")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable("id") Long id, Principal user) throws JSONException {

        return ResponseEntity.ok((BookingDTO) bookingService.getBooking(id, user.getName()));

    }

    @GetMapping(value = "/api/booking/{id}/ad", produces = "application/json")
    @PreAuthorize("hasAuthority('READ_BOOKINGS')")
    public ResponseEntity<Long> getBookingsAd(@PathVariable("id") Long id, Principal user) throws JSONException {
        BookingDTO bookingDto = (BookingDTO) bookingService.getBooking(id, user.getName());
        return ResponseEntity.ok(bookingDto.getAd());
    }
}
