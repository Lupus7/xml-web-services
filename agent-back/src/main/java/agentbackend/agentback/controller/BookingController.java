package agentbackend.agentback.controller;

import agentbackend.agentback.controller.dto.BookDTO;
import agentbackend.agentback.controller.dto.BookingDTO;
import agentbackend.agentback.service.BookingService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Set;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    // Samostalno zauzece
    @PostMapping(value = "/api/booking")
    //@PreAuthorize("hasAuthority('RESERVE_BOOKING')")
    public ResponseEntity<String> reserveBookingRequest(@RequestBody BookDTO bookDto, Principal user) throws JSONException {

        if (bookingService.reserveBookingRequest(bookDto, user.getName()))
            return ResponseEntity.ok("Booking request successfully created!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Prihvatanje bookinga kod agenta
    @PutMapping(value = "/api/booking/{id}")
    //@PreAuthorize("hasAuthority('ACCEPT_BOOKING')")
    public ResponseEntity<String> acceptBookingRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bookingService.acceptBookingRequest(id, user))
            return ResponseEntity.ok("Booking request reserved!");
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

    // Odbijanje booking requesta od strane agenta
    @DeleteMapping(value = "/api/booking/reject/{id}", produces = "application/json", consumes = "application/json")
    //@PreAuthorize("hasAuthority('REJECT_BOOKING')")
    public ResponseEntity<String> rejectBookingRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bookingService.rejectBookingRequest(id, user))
            return ResponseEntity.ok("Booking request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Get all booking that agent got
    @GetMapping(value = "/api/booking/request")
    //@PreAuthorize("hasAuthority('READ_BOOKINGS')")
    public ResponseEntity<Set<BookingDTO>> getAllBookingRequestsFromOthers(Principal user) throws JSONException {

        return ResponseEntity.ok(bookingService.getAllBookingRequestsFromOthers(user.getName()));

    }

    @GetMapping(value = "/api/booking/{id}")
    //@PreAuthorize("hasAuthority('READ_BOOKINGS')")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable("id") Long id, Principal user) throws JSONException {

        return ResponseEntity.ok((BookingDTO) bookingService.getBooking(id, user.getName()));

    }
}


