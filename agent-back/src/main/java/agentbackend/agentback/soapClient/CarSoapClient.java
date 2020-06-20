package agentbackend.agentback.soapClient;

import agentbackend.agentback.config.SoapProperties;
import agentbackend.agentback.model.Car;
import agentbackend.agentback.model.Image;
import com.car_rent.agent_api.wsdl.CarDetails;
import com.car_rent.agent_api.wsdl.PutCarDetailsRequest;
import com.car_rent.agent_api.wsdl.PutCarDetailsResponse;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.List;
import java.util.stream.Collectors;

public class CarSoapClient extends WebServiceGatewaySupport {

    public PutCarDetailsResponse putCar(Car car, List<Image> imageList) {
        PutCarDetailsRequest request = new PutCarDetailsRequest();

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
        carDetails.getImages().addAll(imageList.stream().map(Image::getEncoded64Image).collect(Collectors.toList()));

        request.setCarDetails(carDetails);

        return (PutCarDetailsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.CARS_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/putCarDetailsRequest"));
    }
}
