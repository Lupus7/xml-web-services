package com.example.community.proxy;

import com.example.community.controller.dto.CarDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Principal;
import java.util.List;

@FeignClient(name = "cars-ads")
public interface CarsAdsProxy {

    @GetMapping("/cars/client")
    ResponseEntity<List<CarDTO>> getClientCars(@RequestHeader("Authorization") String auth);

    @PutMapping("/cars/{id}/update/{km}")
    ResponseEntity<String> updateCarKm(@PathVariable("id") Long carId, @PathVariable("km") Double km);

    @GetMapping("/ad/{id}/car")
    ResponseEntity<Long> getCarIdFromAd(@PathVariable("id") Long adId);

}