package com.example.community.proxy;

import org.json.JSONException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@FeignClient(name = "rent")
public interface CarRentProxy {


    @PostMapping(value = "/api/booking/checking")
    ResponseEntity<Boolean> checkingBookingRequests(@RequestBody String jsonObject, @RequestHeader("Authorization") String auth);

    @PutMapping(value = "/api/booking/{id}/end")
    ResponseEntity<String> endBookingRequest(@PathVariable(value = "id") Long id,  @RequestHeader("Authorization") String auth);

}