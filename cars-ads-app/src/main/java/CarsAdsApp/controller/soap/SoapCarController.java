package CarsAdsApp.controller.soap;

import CarsAdsApp.service.CarService;
import CarsAdsApp.soap.SoapProperties;
import com.car_rent.agent_api.car.PutCarDetailsRequest;
import com.car_rent.agent_api.car.PutCarDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapCarController {

    @Autowired
    CarService carService;

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "putCarDetailsRequest")
    @ResponsePayload
    public PutCarDetailsResponse putCar(@RequestPayload PutCarDetailsRequest request) {
        PutCarDetailsResponse response = new PutCarDetailsResponse();
        response.setIsOk(carService.CreateCar(request.getCarDetails()));
        return response;
    }
}
