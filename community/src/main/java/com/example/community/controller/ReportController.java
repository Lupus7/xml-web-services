package com.example.community.controller;

import com.example.community.controller.dto.ReportDto;
import com.example.community.model.Report;
import com.example.community.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/reports")
    public ResponseEntity<String> createReport(@RequestBody ReportDto reportDto, Principal user){
        if(reportService.createReport(reportDto, user.getName())){
            return ResponseEntity.ok("Successfully created...");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/reports")
    public ResponseEntity<ArrayList<ReportDto>> findAll(){
        ArrayList<ReportDto> reportsDto = new ArrayList<>();
        ArrayList<Report> reports = reportService.getAllReports();
        for(Report r: reports){
            ReportDto dto = new ReportDto(r.getExtraInfo(),r.getAllowedMileage(),r.getBooking());
            reportsDto.add(dto);
        }
        return  ResponseEntity.ok(reportsDto);
    }
}
