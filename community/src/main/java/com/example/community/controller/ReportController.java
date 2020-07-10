package com.example.community.controller;

import com.example.community.controller.dto.ReportDto;
import com.example.community.controller.dto.StatisticsDTO;
import com.example.community.model.Report;
import com.example.community.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/reports")
    public ResponseEntity<String> createReport(@RequestBody ReportDto reportDto, Principal user){
        if(reportService.createReport(reportDto, user.getName()) != null){
            return ResponseEntity.ok("Successfully created...");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/reports")
    public ResponseEntity<ArrayList<ReportDto>> findAll(){
        ArrayList<ReportDto> reportsDto = new ArrayList<>();
        ArrayList<Report> reports = reportService.getAllReports();
        for(Report r: reports){
            ReportDto dto = new ReportDto(r.getExtraInfo(),r.getAllowedMileage(),r.getBooking(),0);
            reportsDto.add(dto);
        }
        return  ResponseEntity.ok(reportsDto);
    }

    @GetMapping("/reports/cars/{id}")
    public ResponseEntity<ArrayList<ReportDto>> findAll(@PathVariable("id") Long id, Principal user){
        ArrayList<ReportDto> reportsDto = reportService.getAllReportsForSpecificCar(id, user.getName());
        if(reportsDto != null)
            return ResponseEntity.ok(reportsDto);
        return  ResponseEntity.badRequest().build();
    }

    @GetMapping("/reports/statistics")
    public ResponseEntity<List<StatisticsDTO>> getStatistics(Principal user) {
        List<StatisticsDTO> retVal = reportService.getStatistics(user.getName());
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return  ResponseEntity.badRequest().build();
    }
}
