package agentbackend.agentback.service;

import agentbackend.agentback.controller.dto.CarDTO;
import agentbackend.agentback.controller.dto.ReportDto;
import agentbackend.agentback.controller.dto.StatisticsDTO;
import agentbackend.agentback.model.*;
import agentbackend.agentback.repository.*;
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
    @Autowired
    private RateRepository rateRepository;

    public Boolean createReport(ReportDto reportDto){
        Report report = new Report();
        Booking booking =  bookingRepository.getOne(reportDto.getBooking());
        if(booking == null)
            return false;
        //Ako nije zavrsen request, ne moze da podnese report
        if(booking.getState() != RequestState.PAID)
            return false;
        report.setAllowedMileage(reportDto.getAllowedMileage());
        report.setBooking(reportDto.getBooking());
        report.setExtraInfo(reportDto.getExtraInfo());
        Optional<Booking> book = bookingRepository.findById(reportDto.getBooking());
        if (!book.isPresent())
            return false;
        Optional<Ad> ad = adRepository.findById(book.get().getId());
        if (!ad.isPresent())
            return false;
        Car car = ad.get().getCar();
        if (car == null)
            return false;
        car.setTotalMileage(car.getTotalMileage() + report.getAllowedMileage());

        booking.setState(RequestState.ENDED);
        bookingRepository.save(booking);
        carRepository.save(car);
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
            Ad ad = booking.getAd();
            if (ad == null)
                return null;
            //Uzmi auto oglasa i proveri da li je ID onaj sto trazimo
            Car car = ad.getCar();
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

            Ad ad = booking.getAd();
            if (ad == null)
                return null;

            //Uzmi auto oglasa i proveri da li je ID onaj sto trazimo
            Car car = ad.getCar();
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

    public List<StatisticsDTO> getStatistics(String name) {
        List<StatisticsDTO> statistics = new ArrayList<>();

        List<Car> cars = carRepository.findAllByOwner(name);
        if (cars == null || cars.isEmpty())
            return statistics;

        cars.forEach(car -> {
            StatisticsDTO statisticsDTO = new StatisticsDTO();
            statisticsDTO.setCarId(car.getId());
            statisticsDTO.setCarName(car.getBrand() + " " + car.getModel() + " " + car.getCarClass());
            statisticsDTO.setTotalMileage((long)car.getTotalMileage());
            statisticsDTO.setRating(0.0);
            statisticsDTO.setCommNum(0L);
            rateRepository.findAllByCarId(car.getId()).forEach(rate -> {
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
