package com.example.community.service;

import com.example.community.controller.dto.ReportDto;
import com.example.community.model.Report;
import com.example.community.proxy.CarRentProxy;
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

    public Boolean createReport(ReportDto reportDto, String user) {
        Report report = new Report();
        report.setAllowedMileage(reportDto.getAllowedMileage());
        report.setBooking(reportDto.getBooking());
        report.setExtraInfo(reportDto.getExtraInfo());

        ResponseEntity<String> response = carRentProxy.endBookingRequest(reportDto.getBooking(), user + ";MASTER");
        if (response.getStatusCode().isError())
            return false;

        reportRepository.save(report);

        return true;
    }

    public ArrayList<Report> getAllReports() {
        return (ArrayList<Report>) reportRepository.findAll();
    }

}
