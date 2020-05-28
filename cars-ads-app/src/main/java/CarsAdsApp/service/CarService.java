package CarsAdsApp.service;

import CarsAdsApp.controller.dto.NewCarDTO;
import CarsAdsApp.controller.dto.UpdateCarDTO;
import CarsAdsApp.model.Car;
import CarsAdsApp.model.ObjectFactory;
import CarsAdsApp.model.User;
import CarsAdsApp.model.dto.CarDTO;
import CarsAdsApp.repository.CarRepository;
import CarsAdsApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;
    @Autowired
    UserRepository userRepository;

    //MEthod for creating new car in dataabse
    public boolean CreateCar(NewCarDTO newCarDto,String user) {
        if (newCarDto.getBrand() == null || newCarDto.getModel() == null || newCarDto.getFuel() == null || newCarDto.getCarClass() == null  || newCarDto.getTransmission() == null) {
            return false;
        }
        System.out.println("Des: " + newCarDto.getDescription());
        ObjectFactory factory = new ObjectFactory();
        Car car = factory.createCar();
        car.setAllowedMileage(newCarDto.getAllowedMilleage());
        car.setTotalMileage(newCarDto.getTotalMileage());
        car.setChildrenSeats(newCarDto.getChildrenSeats());
        car.setDescription(newCarDto.getDescription());
        car.setColDamProtection(newCarDto.isColDamProtection());
        car.setOwned(user);
        car.setBrand(newCarDto.getBrand());
        car.setModel(newCarDto.getModel());
        car.setCarClass(newCarDto.getCarClass());
        car.setFuel(newCarDto.getFuel());
        car.setTransmission(newCarDto.getTransmission());
        car.printCar(car);

        //Save car in database
        carRepository.save(car);

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
            carRepository.save(car.get());
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        Optional<Car> car = carRepository.findById(id);
        if (!car.isPresent()) {
            return false;
        }
        carRepository.deleteById(id);
        return true;
    }

    public List<CarDTO> getClientCars(String name) {
        List<CarDTO> carDTOS = new ArrayList<>();
        User user = userRepository.findByEmail(name);
        if (user == null)
            return carDTOS;

        List<Car> cars = carRepository.findAllByOwned(user.getEmail());
        for (Car car : cars)
            carDTOS.add(new CarDTO(car));

        return carDTOS;
    }
}
