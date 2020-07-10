package CarsAdsApp.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user")
public interface UserProxy {

    @GetMapping(value = "/client-control/user/{email}")
    ResponseEntity<Long> getUserId(@PathVariable("email") String email, @RequestHeader("Authorization") String auth);

    @GetMapping(value = "/util/user/{id}")
    ResponseEntity<String> getUserEmail(@PathVariable("id") Long id, @RequestHeader("Authorization") String auth);

    @GetMapping(value = "/util/role/{email}")
    ResponseEntity<String> getUserRole(@PathVariable("email") String email);
}
