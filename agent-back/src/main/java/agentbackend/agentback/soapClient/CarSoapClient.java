package agentbackend.agentback.soapClient;

import agentbackend.agentback.config.SoapProperties;
import agentbackend.agentback.controller.dto.ImageDTO;
import agentbackend.agentback.controller.dto.UpdateCarDTO;
import agentbackend.agentback.model.Car;
import agentbackend.agentback.model.Image;
import com.car_rent.agent_api.wsdl.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.List;
import java.util.stream.Collectors;

public class CarSoapClient extends WebServiceGatewaySupport {

    public CreateCarResponse createCar(Car car) {
        CreateCarRequest request = new CreateCarRequest();

        CarDetails carDetails = new CarDetails();
        carDetails.setId(car.getId());
        carDetails.setOwner(car.getOwner());
        carDetails.setBrand(car.getBrand());
        carDetails.setModel(car.getModel());
        carDetails.setCarClass(car.getCarClass());
        carDetails.setFuel(car.getFuel());
        carDetails.setTransmission(car.getTransmission());
        carDetails.setTotalMileage(car.getTotalMileage());
        carDetails.setAllowedMileage(car.getAllowedMileage());
        carDetails.setChildrenSeats(car.getChildrenSeats());
        carDetails.setColDamProtection(car.isColDamProtection());
        carDetails.setDescription(car.getDescription());

        request.setCarDetails(carDetails);

        return (CreateCarResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.CARS_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/createCarRequest"));
    }

    public EditCarResponse editCar(UpdateCarDTO updateCarDTO, Long id) {
        EditCarRequest request = new EditCarRequest();

        UpdateCarDetails details = new UpdateCarDetails();
        details.setAllowedMileage(updateCarDTO.getAllowedMileage());
        details.setTotalMileage(updateCarDTO.getTotalMileage());
        details.setChildrenSeats(updateCarDTO.getChildrenSeats());
        details.setDescription(updateCarDTO.getDescription());
        details.setChildrenSeats(updateCarDTO.getChildrenSeats());
        details.setBrand(updateCarDTO.getBrand());
        details.setModel(updateCarDTO.getModel());
        details.setCarClass(updateCarDTO.getCarClass());
        details.setFuel(updateCarDTO.getFuel());
        details.setTransmission(updateCarDTO.getTransmission());

        request.setUpdateCarDetails(details);
        request.setId(id);

        return (EditCarResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.CARS_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/editCarRequest"));
    }

    public UpdateCarImagesResponse updateCarImages(ImageDTO imageDTO, Long id) {
        UpdateCarImagesRequest request = new UpdateCarImagesRequest();

        request.getImages().addAll(imageDTO.getImages());
        request.setId(id);

        return (UpdateCarImagesResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.CARS_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/updateCarImagesRequest"));
    }

    public DeleteCarResponse deleteCar(Long id, String email) {
        DeleteCarRequest request = new DeleteCarRequest();

        request.setEmail(email);
        request.setId(id);

        return (DeleteCarResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.CARS_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/deleteCarRequest"));
    }


    // TODO get Cars Soap
}
