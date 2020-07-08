package CarsAdsApp.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user")
public interface UserProxy {

    @GetMapping(value = "/client-control/user/{email}")
    ResponseEntity<Long> getUserId(@PathVariable("email") String email);

    @GetMapping(value = "/util/user/{id}")
    ResponseEntity<String> getUserEmail(@PathVariable("id") Long id);

    @GetMapping(value = "/util/role/{email}")
    ResponseEntity<String> getUserRole(@PathVariable("email") String email);
}
