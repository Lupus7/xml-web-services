package com.example.community.service;

import com.example.community.controller.dto.RateDto;
import com.example.community.model.ObjectFactory;
import com.example.community.model.Rate;
import com.example.community.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    public Boolean craeteRate(RateDto ratedto){
        Rate rate = new Rate();
        rate.setApproved(false);
        rate.setBooking(ratedto.getBooking());
        rate.setComment(ratedto.getComment());
        rate.setRate(ratedto.getRate());
        rateRepository.save(rate);
        return  true;
    }

    public ArrayList<Rate> getAllRates(){
        return (ArrayList<Rate>) rateRepository.findAll();
    }

    public Boolean approveRate(Long id){
        Rate rate = rateRepository.getOne(id);
        if(rate==null)
            return false;
        rate.setApproved(true);
        rateRepository.save(rate);
        return true;
    }

}
