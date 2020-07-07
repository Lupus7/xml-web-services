package CarsAdsApp.proxy;

import CarsAdsApp.model.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@FeignClient(name = "rent")
public interface RentProxy {

    @PostMapping(value = "/api/booking/checking")
    ResponseEntity<Boolean> checkingBookingRequests(@RequestBody String jsonObject, @RequestHeader("Authorization") String auth);

    @PostMapping(value = "/api/booking/checking/remove")
    ResponseEntity<String> deleteCarsBookings(@RequestBody String id, @RequestHeader("Authorization") String auth);

    @GetMapping(value = "/api/booking/state/{id}", produces = "application/json")
    ResponseEntity<Boolean> checkState(@PathVariable("id") Long id , @RequestHeader("Authorization") String auth);
}
