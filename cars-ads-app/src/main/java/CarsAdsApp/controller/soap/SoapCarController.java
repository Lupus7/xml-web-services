package CarsAdsApp.controller.soap;

import CarsAdsApp.model.dto.CarDTO;
import CarsAdsApp.service.CarService;
import CarsAdsApp.soap.SoapProperties;
import com.car_rent.agent_api.*;
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
        response.setId(carService.CreateCar(request.getCarDetails()));
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

    // TODO ostali jos edit, delete i update images
}
