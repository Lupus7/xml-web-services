package agentbackend.agentback.service;


import agentbackend.agentback.controller.dto.CarDTO;
import agentbackend.agentback.controller.dto.CarRateDTO;
import agentbackend.agentback.controller.dto.RateDto;
import agentbackend.agentback.model.Booking;
import agentbackend.agentback.model.Car;
import agentbackend.agentback.model.Image;
import agentbackend.agentback.model.Rate;
import agentbackend.agentback.repository.BookingRepository;
import agentbackend.agentback.repository.CarRepository;
import agentbackend.agentback.repository.ImageRepository;
import agentbackend.agentback.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ImageRepository imageRepository;

    public Boolean createRate(RateDto ratedto, Principal user){
        //Ako ne postoji auto sa zadatim Id-jem, izadji
        Optional<Car> car = carRepository.findById(ratedto.getCarId());
        if(!car.isPresent()){
            return false;
        }
        //TODO: Ako je vec jdnom ostavljen komentar za odredjeni booking, blokiraj spamovanje
        Optional<Booking> rateByBooking = bookingRepository.findById(ratedto.getBooking());
        if(rateByBooking.isPresent()){
            return false;
        }

        Rate rate = new Rate();
        rate.setApproved(false);
        rate.setBooking(ratedto.getBooking());
        rate.setComment(ratedto.getComment());
        rate.setRate(ratedto.getRate());
        rate.setCarId(ratedto.getCarId());
        rate.setRater(user.getName());
        rateRepository.save(rate);
        return  true;
    }

    public ArrayList<Rate> getAllRates(){
        return (ArrayList<Rate>) rateRepository.findAll();
    }

    public ArrayList<Rate> getApprovedRates(){
        ArrayList rates = getAllRates();
        Iterator itr = rates.iterator();
        while(itr.hasNext()){
            Rate rate = (Rate) itr.next();

            if(!rate.isApproved()){
                itr.remove();
            }
        }
        return rates;
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

    public ArrayList<Rate>getAllRatesforSpecificCar(Long carId){
        ArrayList<Rate> rates = getAllRates();
        Iterator it = rates.iterator();
        while(it.hasNext()){
            Rate rate = (Rate) it.next();
            if(rate.getCarId() != carId){
                it.remove();
            }
        }
        return rates;
    }

    public HashMap<String,Integer> getCarsWithMostComment(){
        HashMap<Car,Integer> mostCommented = new HashMap<>();
        ArrayList<Rate> rates = (ArrayList<Rate>) rateRepository.findAll();
        for(Rate rate: rates){
            Car car = carRepository.getOne(rate.getCarId());
            if(car == null)
                return null;
            if(mostCommented.containsKey(car)){
                //Ako je auto vec u mapi, povecaj broj komentara za 1
                mostCommented.put(car, mostCommented.get(car) + 1);
            }else{
                //Ako smo prvi put naisli na ovaj auto, njegov broj komentara postavi na 1
                mostCommented.put(car,1);
            }
        }

        //Sortiranje mape
        HashMap<Car,Integer> sorted = sortByValue(mostCommented);
        HashMap<String,Integer> sortedDtos = new HashMap<>();
        Iterator it = sorted.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            Car car = (Car)pair.getKey();
            CarDTO carDto = new CarDTO();
            carDto.setBrand(car.getBrand());
            carDto.setModel(car.getModel());
            carDto.setFuel(car.getFuel());
            sortedDtos.put(carDto.getBrand() +" " +carDto.getModel() +" " + carDto.getFuel() + " " +carDto.getImages(), (Integer) pair.getValue());
        }
        return sortedDtos;
    }

    public HashMap<String,Double> getCarsWithBestAverageRate(){
        HashMap<Car,Double> bestrated = new HashMap<>();
        ArrayList<Rate> rates = (ArrayList<Rate>) rateRepository.findAll();
        for(Rate rate: rates){
            Car car = carRepository.getOne(rate.getCarId());
            if(car == null)
                return null;
            Double carRate = calculateRatingForCar(car.getId());
            if(!bestrated.containsKey(car)){
                //Ako ako nije vec dodat u mapu, izracunaj njegov rating i dodaj ga.
                bestrated.put(car, carRate);
            }
        }

        //Sortiranje mape
        HashMap<Car,Double> sorted = sortByValueDouble(bestrated);
        HashMap<String,Double> sortedDtos = new HashMap<>();
        Iterator it = sorted.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            Car car = (Car)pair.getKey();
            CarDTO carDto = new CarDTO();
            carDto.setBrand(car.getBrand());
            carDto.setModel(car.getModel());
            carDto.setFuel(car.getFuel());
            sortedDtos.put(carDto.getBrand() +" " +carDto.getModel() +" " + carDto.getFuel() + " " +carDto.getImages(), (Double) pair.getValue());
        }
        return sortedDtos;
    }

    public static HashMap<Car, Integer> sortByValue(HashMap<Car, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Car, Integer> > list =
                new LinkedList<Map.Entry<Car, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Car, Integer>>() {
            public int compare(Map.Entry<Car, Integer> o1,
                               Map.Entry<Car, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Car, Integer> temp = new LinkedHashMap<Car, Integer>();
        for (Map.Entry<Car, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static HashMap<Car, Double> sortByValueDouble(HashMap<Car, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Car, Double> > list =
                new LinkedList<Map.Entry<Car, Double> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Car, Double>>() {
            public int compare(Map.Entry<Car, Double> o1,
                               Map.Entry<Car, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Car, Double> temp = new LinkedHashMap<Car, Double>();
        for (Map.Entry<Car, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public Double calculateRatingForCar(long carId){
        Integer sum = 0;
        Integer count=0;
        ArrayList<Rate> rates = (ArrayList<Rate>) rateRepository.findAll();
        for(Rate rate:rates){
            if(rate.getCarId() == carId){
                sum += rate.getRate();
                count +=1;
            }
        }

        return (double)sum/count;
    }


    public List<CarRateDTO> getRates(Principal user) {

        List<CarRateDTO> carRates = new ArrayList<>();

        List<Car> cars = carRepository.findAllByOwner(user.getName());

        if (cars == null)
            return carRates;

        for (Car car : cars) {
            List<Rate> rates = rateRepository.findAllByCarId(car.getId());
            List<Image> images = imageRepository.findAllByCarId(car.getId());
            for (Rate rate : rates)
                carRates.add(new CarRateDTO(rate, new CarDTO(car, images), user.getName()));
        }

        return carRates;
    }
}
