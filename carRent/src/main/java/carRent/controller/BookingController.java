package carRent.controller;

import carRent.service.BookingService;
import carRent.service.CartService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    // Kreiranje bookinga
    @PostMapping(value = "/api/booking", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> createBookingRequest(@RequestBody String json, Principal user) throws JSONException {

        if (bookingService.createBookingRequest(json, user.getName()))
            return ResponseEntity.ok("Booking request successfully created!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Prihvatanje bookinga kod usera
    @PutMapping(value = "/api/booking/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> acceptBookingRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bookingService.acceptBookingRequest(id, user.getName()))
            return ResponseEntity.ok("Booking request reserved!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }
}
