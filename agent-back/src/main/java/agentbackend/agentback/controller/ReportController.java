package agentbackend.agentback.controller;


import agentbackend.agentback.controller.dto.CarDTO;
import agentbackend.agentback.controller.dto.ReportDto;
import agentbackend.agentback.model.Car;
import agentbackend.agentback.model.Report;
import agentbackend.agentback.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/reports")
    public ResponseEntity<String> createReoport(@RequestBody ReportDto reportDto){
        if(reportService.createReport(reportDto)){
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

    @GetMapping("/reports/cars/{id}")
    public ResponseEntity<ArrayList<ReportDto>> findAll(@PathVariable("id") Long id){
        ArrayList<ReportDto> reportsDto = reportService.getAllReportsForSpecificCar(id);
       if(reportsDto != null)
           return ResponseEntity.ok(reportsDto);
        return  ResponseEntity.badRequest().build();
    }

    @GetMapping("/reports/sorted")
    public ResponseEntity<HashMap<String,Double>> sortCarsByMileage(){
        HashMap<String,Double> sorted = reportService.withMostMileage();
        if(sorted != null)
            return ResponseEntity.ok(sorted);
        return  ResponseEntity.badRequest().build();
    }
}
