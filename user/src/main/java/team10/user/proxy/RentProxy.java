package team10.user.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "rent")
public interface RentProxy {
    @PostMapping(value = "/api/cart")
    ResponseEntity<String> createCart(@RequestHeader("Authorization") String auth, @RequestHeader("Content-Length") String length);
}
