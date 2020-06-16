package agentbackend.agentback.service;


import agentbackend.agentback.controller.dto.RateDto;
import agentbackend.agentback.model.Booking;
import agentbackend.agentback.model.Rate;
import agentbackend.agentback.repository.BookingRepository;
import agentbackend.agentback.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private BookingRepository bookingRepository;

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

    public ArrayList<Rate>getAllRatesForSpecificAd(Long adId){
        ArrayList<Rate> rates = getAllRates();
        Iterator itr = rates.iterator();
        while(itr.hasNext()){
            Rate rate = (Rate) itr.next();
            Booking booking = bookingRepository.getOne(rate.getBooking());
            if(booking.getAd() != adId){
                itr.remove();
            }
        }
        return rates;
    }

}
