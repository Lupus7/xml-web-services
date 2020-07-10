package com.example.community.service;

import com.example.community.controller.dto.ReportDto;
import com.example.community.model.Report;
import com.example.community.proxy.CarRentProxy;
import com.example.community.proxy.CarsAdsProxy;
import com.example.community.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CarRentProxy carRentProxy;
    @Autowired
    private CarsAdsProxy carsAdsProxy;

    public Long createReport(ReportDto reportDto, String user) {
        Report report = new Report();
        report.setAllowedMileage(reportDto.getAllowedMileage());
        report.setBooking(reportDto.getBooking());
        report.setExtraInfo(reportDto.getExtraInfo());

        ResponseEntity<String> response = carRentProxy.endBookingRequest(reportDto.getBooking(), user + ";MASTER");
        if (response.getStatusCode().isError())
            return null;
        //first make a call to get ad id
        ResponseEntity<Long> response2 = carRentProxy.getBookingsAd(reportDto.getBooking(), user+";MASTER");
        if(response2.getStatusCode().isError())
            return null;
        Long adId = response2.getBody();
        System.out.println("AD: " +  adId);
        //then make a call to get car Id
        ResponseEntity<Long> response3 = carsAdsProxy.getCarIdFromAd(adId);
        Long carId = response3.getBody();
        System.out.println("CAR: " + carId);
        //now update mileage
        ResponseEntity<String> response4 = carsAdsProxy.updateCarKm(carId, reportDto.getTravelledKm());
        if(response4.getStatusCode().isError())
            return null;

        reportRepository.save(report);

        return report.getId();
    }

    public ArrayList<Report> getAllReports() {
        return (ArrayList<Report>) reportRepository.findAll();
    }

}
