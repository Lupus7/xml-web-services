package CarsAdsApp.controller.soap;

import CarsAdsApp.controller.dto.ImageDTO;
import CarsAdsApp.controller.dto.UpdateCarDTO;
import CarsAdsApp.model.dto.CarDTO;
import CarsAdsApp.service.CarService;
import CarsAdsApp.soap.SoapProperties;
import com.car_rent.agent_api.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class SoapCarController {

    @Autowired
    CarService carService;

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "createCarRequest")
    @ResponsePayload
    public CreateCarResponse createCar(@RequestPayload CreateCarRequest request) {
        CreateCarResponse response = new CreateCarResponse();
        response.setId(carService.CreateCarSoap(request.getCarDetails()));
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "getCarsRequest")
    @ResponsePayload
    public GetCarsResponse getCars(@RequestPayload GetCarsRequest request) {
        GetCarsResponse response = new ObjectFactory().createGetCarsResponse();
        List<CarDetails> carDetailsList = new ArrayList<>();
        List<CarDTO> cars = carService.getClientCars(request.getEmail());
        for (CarDTO carDTO : cars) {
            CarDetails carDetails = new CarDetails();
            carDetails.setId(carDTO.getCarId());
            carDetails.setAllowedMileage(carDTO.getAllowedMileage());
            carDetails.setTotalMileage(carDTO.getTotalMileage());
            carDetails.setChildrenSeats(carDTO.getChildrenSeats());
            carDetails.setColDamProtection(carDTO.isColDamProtection());
            carDetails.setDescription(carDTO.getDescription());
            carDetails.setBrand(carDTO.getBrand());
            carDetails.setModel(carDTO.getModel());
            carDetails.setCarClass(carDTO.getCarClass());
            carDetails.setFuel(carDTO.getFuel());
            carDetails.setTransmission(carDTO.getTransmission());
            carDetails.setOwner(carDTO.getAdvertiser());

            carDetailsList.add(carDetails);
        }
        response.getCarDetails().addAll(carDetailsList);
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "editCarRequest")
    @ResponsePayload
    public EditCarResponse editCar(@RequestPayload EditCarRequest request) {
        EditCarResponse response = new EditCarResponse();
        UpdateCarDTO updateCarDTO = new UpdateCarDTO();
        updateCarDTO.setAllowedMileage(request.getUpdateCarDetails().getAllowedMileage());
        updateCarDTO.setBrand(request.getUpdateCarDetails().getBrand());
        updateCarDTO.setModel(request.getUpdateCarDetails().getModel());
        updateCarDTO.setCarClass(request.getUpdateCarDetails().getCarClass());
        updateCarDTO.setFuel(request.getUpdateCarDetails().getFuel());
        updateCarDTO.setTransmission(request.getUpdateCarDetails().getTransmission());
        updateCarDTO.setTotalMileage(request.getUpdateCarDetails().getTotalMileage());
        updateCarDTO.setChildrenSeats(request.getUpdateCarDetails().getChildrenSeats());
        updateCarDTO.setColDamProtection(request.getUpdateCarDetails().isColDamProtection());
        updateCarDTO.setImages(request.getUpdateCarDetails().getImages());
        updateCarDTO.setDescription(request.getUpdateCarDetails().getDescription());
        Long id = carService.update(updateCarDTO, request.getId());
        response.setId(id);
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "deleteCarRequest")
    @ResponsePayload
    public DeleteCarResponse deleteCar(@RequestPayload DeleteCarRequest request) throws JSONException {
        DeleteCarResponse response = new DeleteCarResponse();
        boolean delete = carService.delete(request.getId(), request.getEmail());
        response.setResponse(delete);
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "updateCarImagesRequest")
    @ResponsePayload
    public UpdateCarImagesResponse updateCarImages(@RequestPayload UpdateCarImagesRequest request) {
        UpdateCarImagesResponse response = new UpdateCarImagesResponse();
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.getImages().addAll(request.getImages());
        List<Long> images = carService.updateImages(imageDTO,request.getId());
        response.setId(request.getId());
        response.getImages().addAll(images);
        return response;
    }
}
