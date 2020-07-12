package com.example.community.service;

import com.example.community.controller.dto.*;
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
import java.util.Optional;

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

    public ArrayList<Rate> getAllNotApprovedRates() {
        return (ArrayList<Rate>) rateRepository.findAllByApprovedIsFalseAndCommentIsNot("REJECTED!");
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

    public List<RateDto2> getRatesOnly(String user) {

        List<RateDto2> carRates = new ArrayList<>();

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getClientCars(user + ";MASTER");

        if (cars == null || cars.getBody() == null)
            return carRates;

        for (CarDTO carDTO : cars.getBody()) {
            List<Rate> rates = rateRepository.findAllByCarId(carDTO.getCarId());
            for (Rate rate : rates)
                carRates.add(new RateDto2(rate));

        }

        return carRates;
    }

    public Double getCarRate(Long carId) {

        List<Rate> rates = rateRepository.findAllByCarId(carId);
        double x = 0;

        if(rates.size() == 0)
            return x;

        for (Rate rate : rates)
            x += rate.getRate();

        return x / rates.size();


    }

    public Boolean rejectRate(Long id) {
        Rate rate = rateRepository.getOne(id);
        if (rate == null)
            return false;
        rate.setApproved(false);
        rate.setComment("REJECTED!");
        rateRepository.save(rate);
        return true;
    }

    public Boolean recomment(Long rateId, ReCommentDTO reCommentDTO, String user) {
        Optional<Rate> rate = rateRepository.findById(rateId);
        if(!rate.isPresent()){
            return false;
        }
        List<RateDto2> rates = getRatesOnly(user);
        if (rates == null || rates.isEmpty())
            return false;

        for (RateDto2 r : rates) {
            if (r.getId() == rateId) {
                rate.get().setRecomment(reCommentDTO.getRecomment());
                rateRepository.save(rate.get());
                return true;
            }
        }
        return false;
    }
}
