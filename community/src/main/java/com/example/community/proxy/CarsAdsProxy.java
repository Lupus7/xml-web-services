package com.example.community.proxy;

import com.example.community.controller.dto.AdClientDTO;
import com.example.community.controller.dto.CarDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Principal;
import java.util.List;

@FeignClient(name = "cars-ads")
public interface CarsAdsProxy {

    @GetMapping("/cars/client")
    ResponseEntity<List<CarDTO>> getClientCars(@RequestHeader("Authorization") String auth);

    @GetMapping(value = "/api/ad/{id}")
    ResponseEntity<AdClientDTO> getAd(@PathVariable("id") Long id, @RequestHeader("Authorization") String auth);

}