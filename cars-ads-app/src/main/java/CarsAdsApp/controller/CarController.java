package CarsAdsApp.controller;

import CarsAdsApp.controller.dto.NewCarDTO;
import CarsAdsApp.controller.dto.UpdateCarDTO;
import CarsAdsApp.model.Car;
import CarsAdsApp.service.CarService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping("/cars")
    public ResponseEntity<String> postCar(@RequestBody NewCarDTO newCarDto){
        if(carService.CreateCar(newCarDto))
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
    public ResponseEntity<String>deleteCar(@PathVariable Long id){
        boolean deleted = carService.delete(id);
        if(deleted)
            return ResponseEntity.ok("Successfully deleted car from database");
        return ResponseEntity.badRequest().body("Oops.. try again");
    }

}
