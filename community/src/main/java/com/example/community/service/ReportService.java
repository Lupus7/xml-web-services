package com.example.community.service;

import com.example.community.controller.dto.*;
import com.example.community.model.Report;
import com.example.community.proxy.CarRentProxy;
import com.example.community.proxy.CarsAdsProxy;
import com.example.community.repository.RateRepository;
import com.example.community.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private RateRepository rateRepository;

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
        ResponseEntity<String> response4 = carsAdsProxy.updateCarKm(carId, reportDto.getAllowedMileage());
        if(response4.getStatusCode().isError())
            return null;

        reportRepository.save(report);

        return report.getId();
    }

    public ArrayList<Report> getAllReports() {
        return (ArrayList<Report>) reportRepository.findAll();
    }

    public ArrayList<ReportDto> getAllReportsForSpecificCar(long id, String email) {
        ArrayList<ReportDto> reportsDtos = new ArrayList<>();

        if (email == null)
            return reportsDtos;

        ArrayList<Report> reports = (ArrayList<Report>) reportRepository.findAll();

        for (Report r : reports) {
            try {
                ResponseEntity<BookingDTO> booking = carRentProxy.getBooking(r.getBooking(), email + ";MASTER");
                if (booking != null && !booking.getStatusCode().isError() && booking.getBody() != null && booking.getBody().getAd() != null) {
                    ResponseEntity<AdClientDTO> ad = carsAdsProxy.getAd(booking.getBody().getAd(), email + ";MASTER");
                    if (ad != null && !ad.getStatusCode().isError() && ad.getBody() != null && ad.getBody().getCarId() != null && ad.getBody().getCarId() == id) {
                        ReportDto dto = new ReportDto();
                        dto.setAllowedMileage(r.getAllowedMileage());
                        dto.setBooking(r.getBooking());
                        dto.setExtraInfo(r.getExtraInfo());
                        reportsDtos.add(dto);
                    }
                }
            } catch (Exception ignored) {
            }
        }

        return reportsDtos;
    }

    public List<StatisticsDTO> getStatistics(String name) {
        List<StatisticsDTO> statistics = new ArrayList<>();

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getClientCars(name + ";MASTER");
        if (cars == null || cars.getBody() == null || cars.getStatusCode().isError())
            return statistics;

        cars.getBody().forEach(car -> {
            StatisticsDTO statisticsDTO = new StatisticsDTO();
            statisticsDTO.setCarId(car.getCarId());
            statisticsDTO.setCarName(car.getBrand() + " " + car.getModel() + " " + car.getCarClass());
            statisticsDTO.setTotalMileage((long) car.getTotalMileage());
            statisticsDTO.setRating(0.0);
            statisticsDTO.setCommNum(0L);
            rateRepository.findAllByCarId(car.getCarId()).forEach(rate -> {
                statisticsDTO.setRating(statisticsDTO.getRating() + rate.getRate());
                statisticsDTO.setCommNum(statisticsDTO.getCommNum() + 1);
            });
            if (statisticsDTO.getRating() > 0)
                statisticsDTO.setRating(statisticsDTO.getRating() / statisticsDTO.getCommNum());
            statistics.add(statisticsDTO);
        });

        return statistics;
    }
}
