package CarsAdsApp.service;

import CarsAdsApp.controller.dto.UpdateCarDTO;
import CarsAdsApp.model.*;
import CarsAdsApp.model.dto.CarDTO;
import CarsAdsApp.repository.AdRepository;
import CarsAdsApp.repository.CarRepository;
import CarsAdsApp.repository.ImageRepository;
import CarsAdsApp.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    @Autowired
    AdRepository adRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;


    //MEthod for creating new car in dataabse
    public boolean CreateCar(CarDTO newCarDto) {
        if (newCarDto.getBrand() == null || newCarDto.getModel() == null || newCarDto.getFuel() == null || newCarDto.getCarClass() == null || newCarDto.getTransmission() == null) {
            return false;
        }
        ObjectFactory factory = new ObjectFactory();
        Car car = factory.createCar();
        car.setAllowedMileage(newCarDto.getAllowedMileage());
        car.setTotalMileage(newCarDto.getTotalMileage());
        car.setChildrenSeats(newCarDto.getChildrenSeats());
        car.setDescription(newCarDto.getDescription());
        car.setColDamProtection(newCarDto.isColDamProtection());
        car.setOwner("user");
        car.setBrand(newCarDto.getBrand());
        car.setModel(newCarDto.getModel());
        car.setCarClass(newCarDto.getCarClass());
        car.setFuel(newCarDto.getFuel());
        car.setTransmission(newCarDto.getTransmission());

        //Save car in database
        carRepository.save(car);

        for(String image: newCarDto.getImages()){
            Image newImage = new Image(image, car.getId());
            imageRepository.save(newImage);
        }

        return true;
    }

    //Method for retrieving all cars from database
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Optional<Car> findOneById(Long id) {
        return carRepository.findById(id);
    }

    public boolean update(UpdateCarDTO updateCarDTO, Long id) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            car.get().setTotalMileage(updateCarDTO.getTotalMileage());
            car.get().setDescription(updateCarDTO.getDescription());
            car.get().setAllowedMileage(updateCarDTO.getAllowedMileage());
            car.get().setColDamProtection(updateCarDTO.isColDamProtection());
            car.get().setBrand(updateCarDTO.getBrand());
            car.get().setModel(updateCarDTO.getModel());
            car.get().setCarClass(updateCarDTO.getCarClass());
            car.get().setFuel(updateCarDTO.getFuel());
            car.get().setTransmission(updateCarDTO.getTransmission());
            carRepository.save(car.get());
            return true;
        }
        return false;
    }

    public boolean delete(Long id) throws JSONException {
        Optional<Car> car = carRepository.findById(id);
        if (!car.isPresent()) {
            return false;
        }

        List<Ad> ads = adRepository.findAllByCarId(car.get().getId());
        String adsIds = "";
        for (Ad ad : ads) {
            adsIds += ad.getId() + ";";
        }

        JSONObject object = new JSONObject();
        object.put("array", adsIds);
        Boolean check = new RestTemplate().postForObject("http://localhost:8080/rent/api/booking/checking", object, Boolean.class);

        if (!check)
            return false;

        for (Ad ad : ads) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", adsIds);
            new RestTemplate().delete("http://localhost:8080/rent/api/booking/checking/remove/{id}", params);
            adRepository.delete(ad);
        }

        carRepository.deleteById(id);
        return true;
    }

    public List<CarDTO> getClientCars() {
        List<CarDTO> carDTOS = new ArrayList<>();
        User user = userRepository.findByEmail("user");
        if (user == null)
            return carDTOS;

        List<Car> cars = carRepository.findAllByOwner("user");

        for (Car car : cars){
            List<Image> images = imageRepository.findAllByCarId(car.getId());
            CarDTO carDTO = new CarDTO(car);
            for(Image image: images){
                carDTO.getImages().add(image.getEncoded64Image());
            }
            carDTOS.add(carDTO);
        }


        return carDTOS;
    }

    public List<CarDTO> getCarsByBrand(String brand) {
        List<CarDTO> carDTOS = new ArrayList<>();
        List<Car> cars = carRepository.findAllByBrand(brand);
        for (Car car : cars)
            carDTOS.add(new CarDTO(car));

        return carDTOS;

    }

    public List<CarDTO> getCarsByCarClass(String carClass) {
        List<CarDTO> carDTOS = new ArrayList<>();
        List<Car> cars = carRepository.findAllByCarClass(carClass);
        for (Car car : cars)
            carDTOS.add(new CarDTO(car));

        return carDTOS;
    }

    public List<CarDTO> getCarsByModel(String model) {
        List<CarDTO> carDTOS = new ArrayList<>();
        List<Car> cars = carRepository.findAllByModel(model);
        for (Car car : cars)
            carDTOS.add(new CarDTO(car));

        return carDTOS;
    }

    public List<CarDTO> getCarsByFuel(String fuel) {
        List<CarDTO> carDTOS = new ArrayList<>();
        List<Car> cars = carRepository.findAllByFuel(fuel);
        for (Car car : cars)
            carDTOS.add(new CarDTO(car));

        return carDTOS;
    }

    public List<CarDTO> getCarsByTransmission(String transmission) {
        List<CarDTO> carDTOS = new ArrayList<>();
        List<Car> cars = carRepository.findAllByTransmission(transmission);
        for (Car car : cars)
            carDTOS.add(new CarDTO(car));

        return carDTOS;
    }
}
