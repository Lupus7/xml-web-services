package agentbackend.agentback.service;

import agentbackend.agentback.controller.dto.CarDTO;
import agentbackend.agentback.controller.dto.ReportDto;
import agentbackend.agentback.model.*;
import agentbackend.agentback.repository.AdRepository;
import agentbackend.agentback.repository.BookingRepository;
import agentbackend.agentback.repository.CarRepository;
import agentbackend.agentback.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.lang.*;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private CarRepository carRepository;

    public Boolean createReport(ReportDto reportDto){
        Report report = new Report();
        Booking booking =  bookingRepository.getOne(reportDto.getBooking());
        if(booking == null)
            return false;
        //Ako nije zavrsen request, ne moze da podnese report
        if(booking.getState() != RequestState.ENDED)
            return false;
        report.setAllowedMileage(reportDto.getAllowedMileage());
        report.setBooking(reportDto.getBooking());
        report.setExtraInfo(reportDto.getExtraInfo());
        reportRepository.save(report);
        return  true;
    }

    public ArrayList<Report> getAllReports(){ return (ArrayList<Report>) reportRepository.findAll(); }

    public ArrayList<ReportDto>getAllReportsForSpecificCar(Long id){
        ArrayList<Report> reports = (ArrayList<Report>) reportRepository.findAll();
        ArrayList<Report> forCar = new ArrayList<>();
        for(Report r: reports){
            Booking booking =  bookingRepository.getOne(r.getBooking());
            if(booking == null)
                return null;
            Ad ad = adRepository.getOne(booking.getAd());
            if (ad == null)
                return null;
            //Uzmi auto oglasa i proveri da li je ID onaj sto trazimo
            Car car = carRepository.getOne(ad.getCarId());
            if (car == null)
                return null;
            if(car.getId() == id)
                forCar.add(r);
        }
        //Prodji kroz sve reportove za taj auto i kreiraj dto
        ArrayList<ReportDto> reportsDtos = new ArrayList<>();
        for(Report rr: forCar){
            ReportDto dto = new ReportDto();
            dto.setAllowedMileage(rr.getAllowedMileage());
            dto.setBooking(rr.getBooking());
            dto.setExtraInfo(rr.getExtraInfo());
            reportsDtos.add(dto);
        }
        return reportsDtos;
    }

    //Vrati vozila koja imaju najvise predjenih kilometara
    public HashMap<String,Double>withMostMileage(){
        HashMap<Car,Double> carmap = new HashMap<>();
        ArrayList<Report> reports = (ArrayList<Report>) reportRepository.findAll();
        for(Report report: reports){
            Booking booking =  bookingRepository.getOne(report.getBooking());
            if(booking == null)
                return null;

            Ad ad = adRepository.getOne(booking.getAd());
            if (ad == null)
                return null;

            //Uzmi auto oglasa i proveri da li je ID onaj sto trazimo
            Car car = carRepository.getOne(ad.getCarId());
            if (car == null)
                return null;

            if(carmap.containsKey(car)){
                //Ako je auto vec u mapi, na njegovu vrednost predjenih kilometara, dodaj kilometre iz reporta
                carmap.put(car, carmap.get(car) + report.getAllowedMileage());
            }else{
                //Ako smo prvi put naisli na ovaj auto, setuj njegovu vrednostp redjenih kilometara
                carmap.put(car,report.getAllowedMileage());
            }
        }
        //Sortiranje mape
        HashMap<Car,Double> sorted = sortByValue(carmap);
        HashMap<String,Double> sortedDtos = new HashMap<>();
        Iterator it = sorted.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            Car car = (Car)pair.getKey();
            CarDTO carDto = new CarDTO();
            carDto.setBrand(car.getBrand());
            carDto.setModel(car.getModel());
            carDto.setFuel(car.getFuel());
            sortedDtos.put(carDto.getBrand() +" " +carDto.getModel() +" " + carDto.getFuel(), (Double) pair.getValue());
        }
        return sortedDtos;
    }


    public static HashMap<Car, Double> sortByValue(HashMap<Car, Double> hm)
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

}
