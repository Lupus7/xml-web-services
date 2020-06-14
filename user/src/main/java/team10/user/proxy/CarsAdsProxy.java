package team10.user.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import team10.user.models.dto.CarDTO;

import java.util.List;

@FeignClient(name = "cars-ads")
public interface CarsAdsProxy {
    @GetMapping("/cars/client")
    ResponseEntity<List<CarDTO>> getClientCars(@RequestHeader("Authorization") String auth);
    @DeleteMapping(value = "cars/{id}", produces = "application/json")
    ResponseEntity<String> deleteCar(@PathVariable Long id, @RequestHeader("Authorization") String auth);
}
