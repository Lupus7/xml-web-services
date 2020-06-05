package CarsAdsApp.controller;

import CarsAdsApp.controller.dto.UpdateCarDTO;
import CarsAdsApp.model.Car;
import CarsAdsApp.model.dto.CarDTO;
import CarsAdsApp.service.CarService;
import ch.qos.logback.core.net.SyslogOutputStream;
import javassist.NotFoundException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping("/cars")
    public ResponseEntity<String> postCar(@RequestBody CarDTO newCarDto, Principal user){
        if(carService.CreateCar(newCarDto, user.getName()))
            return ResponseEntity.ok("Successfully created");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>>getAllCars(){
        List<Car> cars = carService.getAll();
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("cars/{id}")
    public ResponseEntity<Car> getOne(@PathVariable Long id){
        Optional<Car> car = carService.findOneById(id);
        if(!car.isPresent())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(car.get());
    }

    @PutMapping("cars/{id}")
    public ResponseEntity<String>updateCar(@RequestBody UpdateCarDTO updateCarDTO, @PathVariable Long id){
        if(carService.update(updateCarDTO, id))
            return ResponseEntity.ok("Successfully updated");
        return ResponseEntity.badRequest().body("Oops..try again");
    }

    @DeleteMapping("cars/{id}")
    public ResponseEntity<String>deleteCar(@PathVariable Long id) throws JSONException {
        boolean deleted = carService.delete(id);
        if(deleted)
            return ResponseEntity.ok("Successfully deleted car from database");
        return ResponseEntity.badRequest().body("Oops.. try again");
    }

    // Clients cars
    @GetMapping("/cars/client")
    public ResponseEntity<List<CarDTO>>getClientCars(Principal user){
        return ResponseEntity.ok(carService.getClientCars(user.getName()));
    }

    @GetMapping("/cars/brands/{brand}")
    public ResponseEntity<List<CarDTO>>getCarsByBrand(@PathVariable String brand){
        List<CarDTO> cars = carService.getCarsByBrand(brand);
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/cars/class/{carClass}")
    public ResponseEntity<List<CarDTO>>getCarsByCarClass(@PathVariable String carClass){
        List<CarDTO> cars = carService.getCarsByCarClass(carClass);
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/cars/models/{model}")
    public ResponseEntity<List<CarDTO>>getCarsByModel(@PathVariable String model){
        List<CarDTO> cars = carService.getCarsByModel(model);
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/cars/fuels/{fuel}")
    public ResponseEntity<List<CarDTO>>getCarsByFuel(@PathVariable String fuel){
        List<CarDTO> cars = carService.getCarsByFuel(fuel);
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/cars/transmissions/{transmission}")
    public ResponseEntity<List<CarDTO>>getCarsByTransmission(@PathVariable String transmission){
        List<CarDTO> cars = carService.getCarsByTransmission(transmission);
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }


}
