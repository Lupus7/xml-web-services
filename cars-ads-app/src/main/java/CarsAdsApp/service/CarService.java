package CarsAdsApp.service;

import CarsAdsApp.controller.dto.ImageDTO;
import CarsAdsApp.controller.dto.UpdateCarDTO;
import CarsAdsApp.model.*;
import CarsAdsApp.model.dto.CarDTO;
import CarsAdsApp.proxy.RentProxy;
import CarsAdsApp.proxy.UserProxy;
import CarsAdsApp.repository.AdRepository;
import CarsAdsApp.repository.CarRepository;
import CarsAdsApp.repository.ImageRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.*;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    @Autowired
    AdRepository adRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    RentProxy rentProxy;

    public boolean CreateCar(CarDTO newCarDto, String email) {
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
        car.setOwner(email);
        car.setBrand(newCarDto.getBrand());
        car.setModel(newCarDto.getModel());
        car.setCarClass(newCarDto.getCarClass());
        car.setFuel(newCarDto.getFuel());
        car.setTransmission(newCarDto.getTransmission());

        carRepository.save(car);

        for (String image : newCarDto.getImages()) {
            Image newImage = new Image(image, car.getId());
            imageRepository.save(newImage);
        }

        return true;
    }

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
            car.get().setChildrenSeats(updateCarDTO.getChildrenSeats());
            car.get().setAllowedMileage(updateCarDTO.getAllowedMileage());
            car.get().setColDamProtection(updateCarDTO.isColDamProtection());

            if (updateCarDTO.getDescription() != null && !updateCarDTO.getDescription().equals(""))
                car.get().setDescription(updateCarDTO.getDescription());
            if (updateCarDTO.getBrand() != null && !updateCarDTO.getBrand().equals(""))
                car.get().setBrand(updateCarDTO.getBrand());
            if (updateCarDTO.getModel() != null && !updateCarDTO.getModel().equals(""))
                car.get().setModel(updateCarDTO.getModel());
            if (updateCarDTO.getCarClass() != null && !updateCarDTO.getCarClass().equals(""))
                car.get().setCarClass(updateCarDTO.getCarClass());
            if (updateCarDTO.getFuel() != null && !updateCarDTO.getFuel().equals(""))
                car.get().setFuel(updateCarDTO.getFuel());
            if (updateCarDTO.getTransmission() != null && !updateCarDTO.getTransmission().equals(""))
                car.get().setTransmission(updateCarDTO.getTransmission());

            carRepository.save(car.get());
            return true;
        }
        return false;
    }

    public boolean delete(Long id, Principal user) {


        Optional<Car> car = carRepository.findById(id);
        if (!car.isPresent()) {
            return false;
        }

        List<Ad> ads = adRepository.findAllByCarId(car.get().getId());
        String adsIds = "";
        for (Ad ad : ads) {
            adsIds += ad.getId() + ";";
        }

        ResponseEntity<Boolean> check = rentProxy.checkingBookingRequests(adsIds, user.getName() + ";MASTER");

        if (check == null || !check.getBody())
            return false;

        for (Ad ad : ads) {
            rentProxy.deleteCarsBookings(adsIds, user.getName() + ";MASTER");
            adRepository.delete(ad);
        }

        carRepository.deleteById(id);
        return true;
    }

    public List<CarDTO> getClientCars(String email) {
        List<CarDTO> carDTOS = new ArrayList<>();
        List<Car> cars = carRepository.findAllByOwner(email);

        for (Car car : cars) {
            List<Image> images = imageRepository.findAllByCarId(car.getId());
            CarDTO carDTO = new CarDTO(car);
            for (Image image : images) {
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

    public Boolean updateImages(ImageDTO imagedto, Long id) {
        //first find all images for specific car
        ArrayList<Image> carsImgs = (ArrayList<Image>) imageRepository.findAllByCarId(id);
        //delete all of them, then update
        for(Image image: carsImgs){
            imageRepository.delete(image);
        }
        for(String imageEnc64: imagedto.getImages()){
            Image image = new Image();
            image.setEncoded64Image(imageEnc64);
            image.setCarId(id);
            imageRepository.save(image);
        }
        return true;
    }
}
