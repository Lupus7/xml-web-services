package carRent.controller;

import carRent.model.Booking;
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
import java.util.HashMap;
import java.util.Set;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    // Kreiranje bookinga/bundla
    @PostMapping(value = "/api/booking", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('CREATE_BOOKING')")
    public ResponseEntity<String> createBookingRequest(@RequestBody BundleDTO bundleDTO, Principal user) throws JSONException {

        if (bookingService.createBookingRequest(bundleDTO, user.getName()))
            return ResponseEntity.ok("Booking request successfully created!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Samostalno zauzece bookinga/bundla
    @PostMapping(value = "/api/booking/reserved", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('RESERVE_BOOKING')")
    public ResponseEntity<String> reserveBookingRequest(@RequestBody BundleDTO bundleDTO, Principal user) throws JSONException {

        HashMap<Long, Booking> bookings = bookingService.reserveBookingRequest(bundleDTO, user.getName());
        if(bookings.size() > 0)
            return ResponseEntity.ok("Booking request successfully created!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Prihvatanje bookinga kod clienta
    @PutMapping(value = "/api/booking/{id}")
    @PreAuthorize("hasAuthority('ACCEPT_BOOKING')")
    public ResponseEntity<String> acceptBookingRequest(@PathVariable(value = "id") Long id, Principal user) {

        if (bookingService.acceptBookingRequest(id, user.getName()))
            return ResponseEntity.ok("Booking request reserved!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }


    // Otkazivanje booking requesta od strane clienta koje je zatrazio booking
    @DeleteMapping(value = "/api/booking/{id}")
    @PreAuthorize("hasAuthority('CANCEL_BOOKING')")
    public ResponseEntity<String> cancelBookingRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bookingService.cancelBookingRequest(id, user.getName()))
            return ResponseEntity.ok("Booking request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Booking check
    @PostMapping(value = "/api/booking/checking")
    public ResponseEntity<Boolean> checkingBookingRequests(@RequestBody String jsonObject, Principal user) throws JSONException {
        return ResponseEntity.ok(bookingService.checkingBookingRequests(jsonObject, user.getName()));

    }

    // Odbijanje booking requesta kod brisanja car-a
    @PostMapping(value = "/api/booking/checking/remove")
    public ResponseEntity<String> deleteCarsBookings(@RequestBody String id, Principal user) throws JSONException {

        if (bookingService.deleteCarsBookings(id, user.getName()))
            return ResponseEntity.ok("Booking request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Odbijanje booking requesta od strane vlasnika auta
    @DeleteMapping(value = "/api/booking/reject/{id}")
    @PreAuthorize("hasAuthority('REJECT_BOOKING')")
    public ResponseEntity<String> rejectBookingRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bookingService.rejectBookingRequest(id, user.getName()))
            return ResponseEntity.ok("Booking request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }


    // Get all booking request client sent
    @GetMapping(value = "/api/booking", produces = "application/json")
    @PreAuthorize("hasAuthority('READ_BOOKINGS')")
    public ResponseEntity<ArrayList<BookingDTO>> getAllSentBookingRequests(Principal user) {

        return ResponseEntity.ok((ArrayList<BookingDTO>) bookingService.getAllSentBookingRequests(user.getName()));

    }

    // Get all booking request client received
    @GetMapping(value = "/api/booking/request", produces = "application/json")
    @PreAuthorize("hasAuthority('READ_BOOKINGS')")
    public ResponseEntity<Set<BookingDTO>> getAllReceivedBookingRequests(Principal user) {

        return ResponseEntity.ok(bookingService.getAllReceivedBookingRequests(user.getName()));

    }

    @GetMapping(value = "/api/booking/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('READ_BOOKINGS')")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable("id") Long id, Principal user) {

        return ResponseEntity.ok((BookingDTO) bookingService.getBooking(id, user.getName()));

    }

    @GetMapping(value = "/api/booking/{id}/ad", produces = "application/json")
    @PreAuthorize("hasAuthority('READ_BOOKINGS')")
    public ResponseEntity<Long> getBookingsAd(@PathVariable("id") Long id, Principal user) {
        BookingDTO bookingDto = (BookingDTO) bookingService.getBooking(id, user.getName());
        return ResponseEntity.ok(bookingDto.getAd());
    }

    @PutMapping(value = "/api/booking/{id}/end")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<String> endBookingRequest(@PathVariable(value = "id") Long id, Principal user){

        if (bookingService.endBookingRequest(id, user.getName()))
            return ResponseEntity.ok("Booking request reserved!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }
}
