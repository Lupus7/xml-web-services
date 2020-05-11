package team10.admin.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team10.admin.models.*;
import team10.admin.models.dto.CodebookItemDTO;
import team10.admin.repositories.*;
import team10.admin.util.CodebookItemMapper;

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
    CarRepository carRepository;

    public List<CodebookItemDTO> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(CodebookItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean addBrand(CodebookItemDTO codebookItemDTO) {
        Brand item = new Brand();
        item.setName(codebookItemDTO.getName());
        if (brandRepository.save(item) != null)
            return true;
        return false;
    }

    public boolean updateBrand(CodebookItemDTO codebookItemDTO, Long id) {
        Brand item = brandRepository.getOne(id);
        if (item == null)
            return false;
        item.setName(codebookItemDTO.getName());
        if (brandRepository.save(item) != null)
            return true;
        return false;
    }

    public boolean deleteBrand(Long id) {
        Brand item = brandRepository.getOne(id);
        if (item == null || !item.getModels().isEmpty() || carRepository.findOneByBrandId(id) != null)
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

        if (obj == null || obj.get("name") == null || obj.get("brand") == null )
            return false;

        if (obj.get("brand").equals("") || obj.get("name").equals(""))
            return false;

        Brand brand = brandRepository.findByName((String)obj.get("brand"));
        if(brand == null)
            return false;

        Model item = new Model();
        item.setName((String) obj.get("name"));
        brand.getModels().add(item);

        if(brandRepository.save(brand) != null && modelRepository.save(item) != null)
            return true;
        return false;
    }

    public boolean updateModel(CodebookItemDTO codebookItemDTO, Long id) {
        Model item = modelRepository.getOne(id);
        if (item == null)
            return false;
        item.setName(codebookItemDTO.getName());
        if (modelRepository.save(item) != null)
            return true;
        return false;
    }

    public boolean deleteModel(Long id) {
        Model item = modelRepository.getOne(id);
        if (item == null || carRepository.findOneByModelId(id) != null)
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
        if (carClassRepository.save(item) != null)
            return true;
        return false;
    }

    public boolean updateCarClass(CodebookItemDTO codebookItemDTO, Long id) {
        CarClass item = carClassRepository.getOne(id);
        if (item == null)
            return false;
        item.setName(codebookItemDTO.getName());
        if (carClassRepository.save(item) != null)
            return true;
        return false;
    }

    public boolean deleteCarClass(Long id) {
        CarClass item = carClassRepository.getOne(id);
        if (item == null || carRepository.findOneByCarClassId(id) != null)
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
        if (fuelRepository.save(item) != null)
            return true;
        return false;
    }

    public boolean updateFuel(CodebookItemDTO codebookItemDTO, Long id) {
        Fuel item = fuelRepository.getOne(id);
        if (item == null)
            return false;
        item.setType(codebookItemDTO.getName());
        if (fuelRepository.save(item) != null)
            return true;
        return false;
    }

    public boolean deleteFuel(Long id) {
        Fuel item = fuelRepository.getOne(id);
        if (item == null || carRepository.findOneByFuelId(id) != null)
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
        if (transmissionRepository.save(item) != null)
            return true;
        return false;
    }

    public boolean updateTransmission(CodebookItemDTO codebookItemDTO, Long id) {
        Transmission item = transmissionRepository.getOne(id);
        if (item == null)
            return false;
        item.setType(codebookItemDTO.getName());
        if (transmissionRepository.save(item) != null)
            return true;
        return false;
    }

    public boolean deleteTransmission(Long id) {
        Transmission item = transmissionRepository.getOne(id);
        if (item == null || carRepository.findOneByTransmissionId(id) != null)
            return false;

        transmissionRepository.delete(item);
        return true;
    }
}
