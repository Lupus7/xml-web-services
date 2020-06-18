package com.example.community.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "rent")
public interface CarRentProxy {


    @PostMapping(value = "/api/booking/checking")
    ResponseEntity<Boolean> checkingBookingRequests(@RequestBody String jsonObject, @RequestHeader("Authorization") String auth);

}