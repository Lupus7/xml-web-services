package CarsAdsApp.controller;

import CarsAdsApp.controller.dto.ImageDTO;
import CarsAdsApp.controller.dto.UpdateCarDTO;
import CarsAdsApp.model.Car;
import CarsAdsApp.model.Image;
import CarsAdsApp.model.dto.CarDTO;
import CarsAdsApp.service.CarService;
import ch.qos.logback.core.net.SyslogOutputStream;
import javassist.NotFoundException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping("/cars")
    @PreAuthorize("hasAuthority('CREATE_CAR')")
    public ResponseEntity<Long> postCar(@RequestBody CarDTO newCarDto, Principal user) {
        Long id = carService.CreateCar(newCarDto, user.getName());
        if (id != null)
            return ResponseEntity.ok(id);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/cars")
    @PreAuthorize("hasAuthority('READ_CARS')")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAll();
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("cars/{id}")
    @PreAuthorize("hasAuthority('READ_CAR')")
    public ResponseEntity<Car> getOne(@PathVariable Long id) {
        Optional<Car> car = carService.findOneById(id);
        if (!car.isPresent())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(car.get());
    }

    @PutMapping(value = "cars/{id}")
    @PreAuthorize("hasAuthority('EDIT_CAR')  or hasAuthority('MASTER')")
    public ResponseEntity<String>updateCar(@RequestBody UpdateCarDTO updateCarDTO, @PathVariable Long id){
        if(carService.update(updateCarDTO, id) != null)
            return ResponseEntity.ok("Successfully updated");
        return ResponseEntity.badRequest().body("Oops..try again");
    }

    @DeleteMapping(value = "cars/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('DELETE_CAR') or hasAuthority('MASTER')")
    public ResponseEntity<String> deleteCar(@PathVariable Long id, Principal user) throws JSONException {
        if (carService.delete(id, user.getName()))
            return ResponseEntity.ok("Successfully deleted car from database");
        return ResponseEntity.badRequest().body("Oops.. try again");
    }

    // Clients cars
    @GetMapping("/cars/client")
    //@PreAuthorize("hasAuthority('READ_CLIENT_CARS')")
    public ResponseEntity<List<CarDTO>> getClientCars(Principal user) {
        return ResponseEntity.ok(carService.getClientCars(user.getName()));
    }

    @GetMapping("/cars/brands/{brand}")
    @PreAuthorize("hasAuthority('READ_CODEBOOK') or hasAuthority('MASTER')")
    public ResponseEntity<List<CarDTO>>getCarsByBrand(@PathVariable String brand){
        List<CarDTO> cars = carService.getCarsByBrand(brand);
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/cars/class/{carClass}")
    @PreAuthorize("hasAuthority('READ_CODEBOOK') or hasAuthority('MASTER')")
    public ResponseEntity<List<CarDTO>>getCarsByCarClass(@PathVariable String carClass){
        List<CarDTO> cars = carService.getCarsByCarClass(carClass);
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/cars/models/{model}")
    @PreAuthorize("hasAuthority('READ_CODEBOOK') or hasAuthority('MASTER')")
    public ResponseEntity<List<CarDTO>>getCarsByModel(@PathVariable String model){
        List<CarDTO> cars = carService.getCarsByModel(model);
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/cars/fuels/{fuel}")
    @PreAuthorize("hasAuthority('READ_CODEBOOK') or hasAuthority('MASTER')")
    public ResponseEntity<List<CarDTO>>getCarsByFuel(@PathVariable String fuel){
        List<CarDTO> cars = carService.getCarsByFuel(fuel);
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/cars/transmissions/{transmission}")
    @PreAuthorize("hasAuthority('READ_CODEBOOK') or hasAuthority('MASTER')")
    public ResponseEntity<List<CarDTO>>getCarsByTransmission(@PathVariable String transmission){
        List<CarDTO> cars = carService.getCarsByTransmission(transmission);
        if (cars != null)
            return ResponseEntity.ok(cars);
        return ResponseEntity.badRequest().body(null);
    }

    //Update images for specific car
    @PutMapping("/cars/{id}/images")
    @PreAuthorize("hasAuthority('EDIT_CAR') or hasAuthority('MASTER')")
    public ResponseEntity<String> updateCarImages(@RequestBody ImageDTO imagedto, @PathVariable("id") Long id){
        List<Long> images = carService.updateImages(imagedto, id);
        if(images.size() > 0)
            return ResponseEntity.ok("Successfully updated images");
        return ResponseEntity.badRequest().build();
    }


}
