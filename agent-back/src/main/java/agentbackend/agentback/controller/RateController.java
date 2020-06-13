package agentbackend.agentback.controller;


import agentbackend.agentback.controller.dto.RateDto;
import agentbackend.agentback.model.Rate;
import agentbackend.agentback.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

@RestController
public class RateController {

    @Autowired
    RateService rateService;

    @PostMapping("/rates")
    public ResponseEntity<String> leaveRate(@RequestBody RateDto rateDto){
        Boolean succeed = rateService.craeteRate(rateDto);
        if(succeed)
            return ResponseEntity.ok("Successfully left rate");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/rates")
    public ResponseEntity<ArrayList<RateDto>> findAll(){
        ArrayList<Rate> rates = rateService.getAllRates();
        ArrayList<RateDto> dtos = new ArrayList<>();
        if(rates != null){
            for(Rate rate: rates){
                RateDto newRate = new RateDto(rate.getRate(), rate.getComment(), rate.getBooking());
                dtos.add(newRate);
            }
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/rates/{id}/approve")
    public ResponseEntity<String> approveRate(@PathParam("id")Long id){
        Boolean approved = rateService.approveRate(id);
        if (approved)
            return ResponseEntity.ok("Successfull");
        return ResponseEntity.badRequest().build();
    }

}
