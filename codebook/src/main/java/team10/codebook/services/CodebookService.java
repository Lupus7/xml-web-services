package team10.codebook.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team10.codebook.models.*;
import team10.codebook.models.dto.CarDTO;
import team10.codebook.models.dto.CodebookItemDTO;
import team10.codebook.models.dto.UpdateCarDTO;
import team10.codebook.proxy.CarsAdsProxy;
import team10.codebook.repositories.*;
import team10.codebook.util.CodebookItemMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodebookService {
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    CarClassRepository carClassRepository;

    @Autowired
    FuelRepository fuelRepository;

    @Autowired
    TransmissionRepository transmissionRepository;

    @Autowired
    CarsAdsProxy carsAdsProxy;

    public List<CodebookItemDTO> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(CodebookItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean addBrand(CodebookItemDTO codebookItemDTO) {
        Brand item = new Brand(codebookItemDTO.getName());
        brandRepository.save(item);
        System.out.println(item.getId());
        return true;
    }

    public boolean updateBrand(CodebookItemDTO codebookItemDTO, Long id) {
        Brand item = brandRepository.getOne(id);
        if (item == null)
            return false;

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getCarsByBrand(item.getName(), "NONE;MASTER");
        if (cars.getBody() != null && cars.getBody().size() > 0) {
            for (CarDTO car : cars.getBody()) {
                UpdateCarDTO updateCarDTO = new UpdateCarDTO();
                updateCarDTO.setAllowedMileage(car.getAllowedMileage());
                updateCarDTO.setBrand(codebookItemDTO.getName());
                updateCarDTO.setCarClass(car.getCarClass());
                updateCarDTO.setChildrenSeats(car.getChildrenSeats());
                updateCarDTO.setColDamProtection(car.isColDamProtection());
                updateCarDTO.setDescription(car.getDescription());
                updateCarDTO.setFuel(car.getFuel());
                updateCarDTO.setImages(car.getImages());
                updateCarDTO.setModel(car.getModel());
                updateCarDTO.setTotalMileage(car.getTotalMileage());
                updateCarDTO.setTransmission(car.getTransmission());

                carsAdsProxy.updateCar(updateCarDTO, car.getCarId(), "NONE;MASTER");
            }
        } else
            System.out.println("nema brate");
        item.setName(codebookItemDTO.getName());
        brandRepository.save(item);
        return true;
    }

    public boolean deleteBrand(Long id) {
        Brand item = brandRepository.getOne(id);
        if (item == null || !item.getModels().isEmpty())
            return false;

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getCarsByBrand(item.getName(), "NONE;MASTER");
        if (cars.getBody() != null && cars.getBody().size() > 0)
            return false;

        brandRepository.delete(item);
        return true;
    }

    public List<CodebookItemDTO> getAllModels() {
        return modelRepository.findAll()
                .stream()
                .map(CodebookItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean addModel(String codebookItemDTO) throws JSONException {
        JSONObject obj = new JSONObject(codebookItemDTO);

        if (obj == null || obj.get("name") == null || obj.get("brand") == null)
            return false;

        if (obj.get("brand").equals("") || obj.get("name").equals(""))
            return false;

        Brand brand = brandRepository.findByName((String) obj.get("brand"));
        if (brand == null)
            return false;

        Model item = new Model();
        item.setName((String) obj.get("name"));
        brand.getModels().add(item);

        brandRepository.save(brand);
        return true;
    }

    public boolean updateModel(CodebookItemDTO codebookItemDTO, Long id) {
        Model item = modelRepository.getOne(id);
        if (item == null)
            return false;

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getCarsByModel(item.getName(), "NONE;MASTER");
        if (cars.getBody() != null && cars.getBody().size() > 0) {
            for (CarDTO car : cars.getBody()) {
                UpdateCarDTO updateCarDTO = new UpdateCarDTO();
                updateCarDTO.setAllowedMileage(car.getAllowedMileage());
                updateCarDTO.setBrand(car.getBrand());
                updateCarDTO.setCarClass(car.getCarClass());
                updateCarDTO.setChildrenSeats(car.getChildrenSeats());
                updateCarDTO.setColDamProtection(car.isColDamProtection());
                updateCarDTO.setDescription(car.getDescription());
                updateCarDTO.setFuel(car.getFuel());
                updateCarDTO.setImages(car.getImages());
                updateCarDTO.setModel(codebookItemDTO.getName());
                updateCarDTO.setTotalMileage(car.getTotalMileage());
                updateCarDTO.setTransmission(car.getTransmission());

                carsAdsProxy.updateCar(updateCarDTO, car.getCarId(), "NONE;MASTER");
            }
        }

        item.setName(codebookItemDTO.getName());
        modelRepository.save(item);
        return true;
    }

    public boolean deleteModel(Long id) {
        Model item = modelRepository.getOne(id);
        if (item == null)
            return false;

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getCarsByModel(item.getName(), "NONE;MASTER");
        if (cars.getBody() != null && cars.getBody().size() > 0)
            return false;

        List<Brand> brands = brandRepository.findAll()
                .stream()
                .filter(brand -> brand.getModels().contains(item))
                .collect(Collectors.toList());

        brands.forEach(brand -> {
            brand.getModels().remove(item);
            brandRepository.save(brand);
        });

        modelRepository.delete(item);

        return true;
    }

    public List<CodebookItemDTO> getAllClasses() {
        return carClassRepository.findAll()
                .stream()
                .map(CodebookItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean addCarClass(CodebookItemDTO codebookItemDTO) {
        CarClass item = new CarClass();
        item.setName(codebookItemDTO.getName());
        carClassRepository.save(item);
        return true;
    }

    public boolean updateCarClass(CodebookItemDTO codebookItemDTO, Long id) {
        CarClass item = carClassRepository.getOne(id);
        if (item == null)
            return false;

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getCarsByCarClass(item.getName(), "NONE;MASTER");
        if (cars.getBody() != null && cars.getBody().size() > 0) {
            for (CarDTO car : cars.getBody()) {
                UpdateCarDTO updateCarDTO = new UpdateCarDTO();
                updateCarDTO.setAllowedMileage(car.getAllowedMileage());
                updateCarDTO.setBrand(car.getBrand());
                updateCarDTO.setCarClass(codebookItemDTO.getName());
                updateCarDTO.setChildrenSeats(car.getChildrenSeats());
                updateCarDTO.setColDamProtection(car.isColDamProtection());
                updateCarDTO.setDescription(car.getDescription());
                updateCarDTO.setFuel(car.getFuel());
                updateCarDTO.setImages(car.getImages());
                updateCarDTO.setModel(car.getModel());
                updateCarDTO.setTotalMileage(car.getTotalMileage());
                updateCarDTO.setTransmission(car.getTransmission());

                carsAdsProxy.updateCar(updateCarDTO, car.getCarId(), "NONE;MASTER");
            }
        }

        item.setName(codebookItemDTO.getName());
        carClassRepository.save(item);
        return true;
    }

    public boolean deleteCarClass(Long id) {
        CarClass item = carClassRepository.getOne(id);
        if (item == null)
            return false;

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getCarsByCarClass(item.getName(), "NONE;MASTER");
        if (cars.getBody() != null && cars.getBody().size() > 0)
            return false;

        carClassRepository.delete(item);
        return true;
    }

    public List<CodebookItemDTO> getAllFuels() {
        return fuelRepository.findAll()
                .stream()
                .map(CodebookItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean addFuel(CodebookItemDTO codebookItemDTO) {
        Fuel item = new Fuel();
        item.setType(codebookItemDTO.getName());
        fuelRepository.save(item);
        return true;
    }

    public boolean updateFuel(CodebookItemDTO codebookItemDTO, Long id) {
        Fuel item = fuelRepository.getOne(id);
        if (item == null)
            return false;

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getCarsByFuel(item.getType(), "NONE;MASTER");
        if (cars.getBody() != null && cars.getBody().size() > 0) {
            for (CarDTO car : cars.getBody()) {
                UpdateCarDTO updateCarDTO = new UpdateCarDTO();
                updateCarDTO.setAllowedMileage(car.getAllowedMileage());
                updateCarDTO.setBrand(car.getBrand());
                updateCarDTO.setCarClass(car.getCarClass());
                updateCarDTO.setChildrenSeats(car.getChildrenSeats());
                updateCarDTO.setColDamProtection(car.isColDamProtection());
                updateCarDTO.setDescription(car.getDescription());
                updateCarDTO.setFuel(codebookItemDTO.getName());
                updateCarDTO.setImages(car.getImages());
                updateCarDTO.setModel(car.getModel());
                updateCarDTO.setTotalMileage(car.getTotalMileage());
                updateCarDTO.setTransmission(car.getTransmission());

                carsAdsProxy.updateCar(updateCarDTO, car.getCarId(), "NONE;MASTER");
            }
        }

        item.setType(codebookItemDTO.getName());
        fuelRepository.save(item);
        return true;
    }

    public boolean deleteFuel(Long id) {
        Fuel item = fuelRepository.getOne(id);
        if (item == null)
            return false;

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getCarsByFuel(item.getType(), "NONE;MASTER");
        if (cars.getBody() != null && cars.getBody().size() > 0)
            return false;

        fuelRepository.delete(item);
        return true;
    }

    public List<CodebookItemDTO> getAllTransmissions() {
        return transmissionRepository.findAll()
                .stream()
                .map(CodebookItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean addTransmission(CodebookItemDTO codebookItemDTO) {
        Transmission item = new Transmission();
        item.setType(codebookItemDTO.getName());
        transmissionRepository.save(item);
        return true;
    }

    public boolean updateTransmission(CodebookItemDTO codebookItemDTO, Long id) {
        Transmission item = transmissionRepository.getOne(id);
        if (item == null)
            return false;

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getCarsByTransmission(item.getType(), "NONE;MASTER");
        if (cars.getBody() != null && cars.getBody().size() > 0) {
            for (CarDTO car : cars.getBody()) {
                UpdateCarDTO updateCarDTO = new UpdateCarDTO();
                updateCarDTO.setAllowedMileage(car.getAllowedMileage());
                updateCarDTO.setBrand(car.getBrand());
                updateCarDTO.setCarClass(car.getCarClass());
                updateCarDTO.setChildrenSeats(car.getChildrenSeats());
                updateCarDTO.setColDamProtection(car.isColDamProtection());
                updateCarDTO.setDescription(car.getDescription());
                updateCarDTO.setFuel(car.getFuel());
                updateCarDTO.setImages(car.getImages());
                updateCarDTO.setModel(car.getModel());
                updateCarDTO.setTotalMileage(car.getTotalMileage());
                updateCarDTO.setTransmission(codebookItemDTO.getName());

                carsAdsProxy.updateCar(updateCarDTO, car.getCarId(), "NONE;MASTER");
            }
        }

        item.setType(codebookItemDTO.getName());
        transmissionRepository.save(item);
        return true;
    }

    public boolean deleteTransmission(Long id) {
        Transmission item = transmissionRepository.getOne(id);
        if (item == null)
            return false;

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getCarsByTransmission(item.getType(), "NONE;MASTER");
        if (cars.getBody() != null && cars.getBody().size() > 0)
            return false;

        transmissionRepository.delete(item);
        return true;
    }
}
