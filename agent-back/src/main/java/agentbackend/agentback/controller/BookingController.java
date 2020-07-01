package agentbackend.agentback.controller;

import agentbackend.agentback.controller.dto.BookDTO;
import agentbackend.agentback.controller.dto.BookingDTO;
import agentbackend.agentback.controller.dto.BundleDTO;
import agentbackend.agentback.model.Booking;
import agentbackend.agentback.service.BookingService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    // Samostalno zauzece
    @PostMapping(value = "/api/booking")
    public ResponseEntity<String> reserveBookingRequest(@RequestBody BundleDTO bundleDTO, Principal user) {
        HashMap<Long, Booking> map = bookingService.reserveBookingRequest(bundleDTO, user.getName());
        if (map.size() > 0)
            return ResponseEntity.ok("Booking request successfully created!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Prihvatanje bookinga kod agenta
    @PutMapping(value = "/api/booking/{id}")
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
    @DeleteMapping(value = "/api/booking/reject/{id}")
    public ResponseEntity<String> rejectBookingRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bookingService.rejectBookingRequest(id, user))
            return ResponseEntity.ok("Booking request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Get all booking that agent got
    @GetMapping(value = "/api/booking/request")
    public ResponseEntity<Set<BookingDTO>> getAllReceivedBookingRequests(Principal user) throws JSONException {

        return ResponseEntity.ok(bookingService.getAllReceivedBookingRequests(user.getName()));

    }

    @GetMapping(value = "/api/booking/{id}", produces = "application/json")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable("id") Long id, Principal user) throws JSONException {

        return ResponseEntity.ok((BookingDTO) bookingService.getBooking(id, user.getName()));

    }
}


