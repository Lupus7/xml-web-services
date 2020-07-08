package com.example.community.controller.soap;

import com.car_rent.agent_api.*;
import com.example.community.controller.dto.ReportDto;
import com.example.community.model.Report;
import com.example.community.service.ReportService;
import com.example.community.soap.SoapProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class SoapReportController {
    @Autowired
    ReportService reportService;

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "createReportRequest")
    @ResponsePayload
    public CreateReportResponse createReport(@RequestPayload CreateReportRequest request) {
        CreateReportResponse response = new ObjectFactory().createCreateReportResponse();

        ReportDto reportDto = new ReportDto();
        reportDto.setAllowedMileage(request.getReport().getMileage());
        reportDto.setBooking(request.getReport().getBooking());
        reportDto.setExtraInfo(request.getReport().getExtraInfo());

        Long id = reportService.createReport(reportDto, request.getUser());

        if (id == null)
            id = -1L;

        response.setId(id);

        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "getReportsRequest")
    @ResponsePayload
    public GetReportsResponse getReports(@RequestPayload GetReportsRequest request) {
        GetReportsResponse response = new ObjectFactory().createGetReportsResponse();

        List<Report> reports = reportService.getAllReports();

        response.getReports().addAll(
                reports
                        .stream()
                        .map(report -> {
                            ReportDetails details = new ReportDetails();
                            details.setBooking(report.getBooking());
                            details.setExtraInfo(report.getExtraInfo());
                            details.setId(report.getId());
                            details.setMileage(report.getAllowedMileage());
                            return details;
                        })
                        .collect(Collectors.toList())
        );

        return response;
    }
}
