package com.example.community.controller;

import com.example.community.controller.dto.CarRateDTO;
import com.example.community.controller.dto.RateDto;
import com.example.community.model.Rate;
import com.example.community.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RateController {

    @Autowired
    RateService rateService;

    @PostMapping("/rate")
    public ResponseEntity<String> leaveRate(@RequestBody  RateDto rateDto, Principal user){
        Boolean succeed = rateService.createRate(rateDto, user);
        if(succeed)
            return ResponseEntity.ok("Successfully left rate");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/rate/{id}/approve")
    public ResponseEntity<String> approveRate(@PathParam("id")Long id){
        Boolean approved = rateService.approveRate(id);
        if (approved)
            return ResponseEntity.ok("Successfull");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/rate")
    public ResponseEntity<List<CarRateDTO>> getRates(Principal user){
        return ResponseEntity.ok(rateService.getRates(user));
    }

}
