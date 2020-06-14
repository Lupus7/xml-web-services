package com.example.community.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@FeignClient(name = "carRent")
public interface CarRentProxy {


    @PostMapping(value = "/api/booking/checking")
    ResponseEntity<Boolean> checkingBookingRequests(@RequestBody String jsonObject, Principal user);

    @GetMapping(value = "/api/booking/{id}/ad", produces = "application/json")
    ResponseEntity<Long> getBookingsAd(@PathVariable("id") Long id, Principal user);

    }