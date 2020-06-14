package CarsAdsApp.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Principal;

@FeignClient(name = "rent")
public interface RentProxy {

    @PostMapping(value = "/api/booking/checking")
    ResponseEntity<Boolean> checkingBookingRequests(@RequestBody String jsonObject, @RequestHeader("Authorization") String auth);

    @PostMapping(value = "/api/booking/checking/remove")
    ResponseEntity<String> deleteCarsBookings(@RequestBody String id, @RequestHeader("Authorization") String auth);
}
