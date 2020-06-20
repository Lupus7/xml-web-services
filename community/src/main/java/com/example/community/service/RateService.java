package com.example.community.service;

import com.example.community.controller.dto.CarDTO;
import com.example.community.controller.dto.CarRateDTO;
import com.example.community.controller.dto.RateDto;
import com.example.community.model.ObjectFactory;
import com.example.community.model.Rate;
import com.example.community.proxy.CarsAdsProxy;
import com.example.community.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    CarsAdsProxy carsAdsProxy;

    public Boolean createRate(RateDto ratedto, Principal user) {

        Rate rateCheck = rateRepository.findByBooking(ratedto.getBooking());

        if (rateCheck != null)
            return false;

        ObjectFactory objectFactory = new ObjectFactory();
        Rate rate = objectFactory.createRate();
        rate.setApproved(false);
        rate.setBooking(ratedto.getBooking());
        rate.setComment(ratedto.getComment());
        rate.setRate(ratedto.getRate());
        rate.setCarId(ratedto.getCarId());
        rate.setRater(user.getName());
        rateRepository.save(rate);
        return true;
    }

    public ArrayList<Rate> getAllRates() {
        return (ArrayList<Rate>) rateRepository.findAll();
    }

    public Boolean approveRate(Long id) {
        Rate rate = rateRepository.getOne(id);
        if (rate == null)
            return false;
        rate.setApproved(true);
        rateRepository.save(rate);
        return true;
    }

    public List<CarRateDTO> getRates(Principal user) {

        List<CarRateDTO> carRates = new ArrayList<>();

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getClientCars(user.getName() + ";MASTER");

        if (cars == null || cars.getBody() == null)
            return carRates;

        for (CarDTO carDTO : cars.getBody()) {
            List<Rate> rates = rateRepository.findAllByCarId(carDTO.getCarId());
            for (Rate rate : rates)
                carRates.add(new CarRateDTO(rate, carDTO));

        }


        return carRates;
    }

    public Double getCarRate(Long carId) {

        List<Rate> rates = rateRepository.findAllByCarId(carId);
        double x = 0;
        for (Rate rate : rates)
            x += rate.getRate();

        return x / rates.size();


    }
}
