package agentbackend.agentback.service;


import agentbackend.agentback.controller.dto.CarDTO;
import agentbackend.agentback.controller.dto.ImageDTO;
import agentbackend.agentback.controller.dto.UpdateCarDTO;
import agentbackend.agentback.model.*;
import agentbackend.agentback.model.ObjectFactory;
import agentbackend.agentback.repository.*;
import agentbackend.agentback.soapClient.BookingSoapClient;
import agentbackend.agentback.soapClient.CarSoapClient;
import agentbackend.agentback.soapClient.SpecSoapClient;
import com.car_rent.agent_api.wsdl.*;
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
    ImageRepository imageRepository;

    @Autowired
    CarSoapClient carSoapClient;

    @Autowired
    BookingSoapClient bookingSoapClient;

    @Autowired
    SpecSoapClient specSoapClient;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    public Long CreateCar(CarDTO newCarDto, String email) {
        if (newCarDto.getBrand() == null || newCarDto.getModel() == null || newCarDto.getFuel() == null || newCarDto.getCarClass() == null || newCarDto.getTransmission() == null) {
            return null;
        }
        ObjectFactory factory = new ObjectFactory();
        Car car = factory.createCar();
        car.setAllowedMileage(newCarDto.getAllowedMileage());
        car.setTotalMileage(newCarDto.getTotalMileage());
        car.setChildrenSeats(newCarDto.getChildrenSeats());
        car.setDescription(newCarDto.getDescription());
        car.setColDamProtection(newCarDto.isColDamProtection());
        car.setBrand(newCarDto.getBrand());
        car.setModel(newCarDto.getModel());
        car.setCarClass(newCarDto.getCarClass());
        car.setFuel(newCarDto.getFuel());
        car.setTransmission(newCarDto.getTransmission());
        car.setOwner(email);

        CreateCarResponse response = carSoapClient.createCar(car);
        car.setServiceId(response.getId());
        carRepository.save(car);

        return car.getId();
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
            car.get().setChildrenSeats(updateCarDTO.getChildrenSeats());
            carRepository.save(car.get());
            carSoapClient.editCar(updateCarDTO, car.get().getServiceId());

            return true;
        }
        return false;
    }

    public Boolean updateImages(ImageDTO imagedto, Long id) {

        if (imagedto.getImages().size() == 0 || imagedto.getImages().size() > 6)
            return false;

        Optional<Car> car = carRepository.findById(id);
        if (!car.isPresent())
            return false;

        UpdateCarImagesResponse response = carSoapClient.updateCarImages(imagedto, car.get().getServiceId());

        ArrayList<Image> carsImgs = (ArrayList<Image>) imageRepository.findAllByCarId(id);
        for (Image image : carsImgs)
            imageRepository.delete(image);

        for (int i = 0; i < imagedto.getImages().size(); i++) {
            Image image = new Image();
            image.setEncoded64Image(imagedto.getImages().get(i));
            image.setCar(car.get());
            image.setServiceId(response.getImages().get(i));
            imageRepository.save(image);
        }
        return true;
    }


    public boolean delete(Long id, String email) throws JSONException {
        Optional<Car> car = carRepository.findById(id);
        if (!car.isPresent()) {
            return false;
        }


        List<Ad> ads = adRepository.findAllByCar(car.get());
        String adsIds = "";
        if (ads.size() > 0)
            for (Ad ad : ads)
                adsIds += ad.getId() + ";";
        else
            adsIds = "NONE";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ads", adsIds);

        CheckingBookingResponse check = bookingSoapClient.checkingBooking(jsonObject.toString(), email);
        if (!check.isResponse())
            return false;

        for (Ad ad : ads) {
            bookingSoapClient.deleteBooking(jsonObject.toString(), email);
            adRepository.delete(ad);
        }

        DeleteCarResponse response = carSoapClient.deleteCar(car.get().getServiceId(), email);
        if (!response.isResponse())
            return false;

        List<Image> images = imageRepository.findAllByCar(car.get());
        for (Image i : images)
            imageRepository.delete(i);

        carRepository.delete(car.get());
        return true;
    }

    public List<CarDTO> getClientCars(String email) {


        GetCarsResponse response = carSoapClient.getCars(email);
        if (response != null) {
            for (CarDetailsSoap carDetail : response.getCarDetails()) {
                Car carCheck = carRepository.findByServiceId(carDetail.getId());
                if (carCheck == null) {
                    Car car = new Car();
                    car.setServiceId(carDetail.getId());
                    car.setAllowedMileage(carDetail.getAllowedMileage());
                    car.setTotalMileage(carDetail.getTotalMileage());
                    car.setColDamProtection(carDetail.isColDamProtection());
                    car.setChildrenSeats(carDetail.getChildrenSeats());
                    car.setBrand(carDetail.getBrand());
                    car.setModel(carDetail.getModel());
                    car.setCarClass(carDetail.getCarClass());
                    car.setFuel(carDetail.getFuel());
                    car.setTransmission(carDetail.getTransmission());
                    car.setDescription(carDetail.getDescription());
                    car.setOwner(carDetail.getOwner());
                    carRepository.save(car);
                    for (ImageDetails img : carDetail.getImages()) {
                        Image image = new Image();
                        image.setServiceId(img.getId());
                        image.setEncoded64Image(img.getSrc());
                        image.setCar(car);
                        imageRepository.save(image);
                    }
                } else {
                    if (carCheck.getAllowedMileage() != carDetail.getAllowedMileage())
                        carCheck.setAllowedMileage(carDetail.getAllowedMileage());
                    if (carCheck.getTotalMileage() != carDetail.getTotalMileage())
                        carCheck.setTotalMileage(carDetail.getTotalMileage());
                    if (carCheck.getChildrenSeats() != carDetail.getChildrenSeats())
                        carCheck.setChildrenSeats(carDetail.getChildrenSeats());
                    if (carCheck.isColDamProtection() != carDetail.isColDamProtection())
                        carCheck.setColDamProtection(carDetail.isColDamProtection());
                    if (!carCheck.getDescription().equals(carDetail.getDescription()))
                        carCheck.setDescription(carDetail.getDescription());
                    if (!carCheck.getBrand().equals(carDetail.getBrand()))
                        carCheck.setBrand(carDetail.getBrand());
                    if (!carCheck.getModel().equals(carDetail.getModel()))
                        carCheck.setModel(carDetail.getModel());
                    if (!carCheck.getCarClass().equals(carDetail.getCarClass()))
                        carCheck.setCarClass(carDetail.getCarClass());
                    if (!carCheck.getFuel().equals(carDetail.getFuel()))
                        carCheck.setFuel(carDetail.getFuel());
                    if (!carCheck.getTransmission().equals(carDetail.getTransmission()))
                        carCheck.setTransmission(carDetail.getTransmission());

                    carRepository.save(carCheck);

                    List<Image> images = imageRepository.findAllByCar(carCheck);
                    for (ImageDetails imgDetail : carDetail.getImages()) {
                        for (Image img : images) {
                            if (imgDetail.getId() == img.getServiceId()) {
                                if (!imgDetail.getSrc().equals(img.getEncoded64Image())) {
                                    img.setEncoded64Image(imgDetail.getSrc());
                                    imageRepository.save(img);
                                }
                            }
                        }
                    }


                }
            }
        }


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

    public GetAllSpecsResponse getAllSpecs(String name) {
        return specSoapClient.getAllSpecs(name);
    }

    //Method for retrieving all cars from database
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Optional<Car> findOneById(Long id) {
        return carRepository.findById(id);
    }
}
