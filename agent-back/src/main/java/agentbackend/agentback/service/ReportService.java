package agentbackend.agentback.service;

import agentbackend.agentback.controller.dto.ReportDto;
import agentbackend.agentback.model.Report;
import agentbackend.agentback.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Boolean createReport(ReportDto reportDto){
        Report report = new Report();
        report.setAllowedMileage(reportDto.getAllowedMileage());
        report.setBooking(reportDto.getBooking());
        report.setExtraInfo(reportDto.getExtraInfo());
        reportRepository.save(report);
        return  true;
    }

    public ArrayList<Report> getAllReports(){ return (ArrayList<Report>) reportRepository.findAll(); }

}
