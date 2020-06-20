package CarsAdsApp.controller.soap;

import CarsAdsApp.service.AdService;
import CarsAdsApp.soap.SoapProperties;
import com.car_rent.agent_api.ad.PutActivateAdRequest;
import com.car_rent.agent_api.ad.PutActivateAdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapAdController {

    @Autowired
    AdService adService;

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "putActivateAdRequest")
    @ResponsePayload
    public PutActivateAdResponse activateAd(@RequestPayload PutActivateAdRequest request) {
        PutActivateAdResponse response = new PutActivateAdResponse();
        adService.activateAd(request.getId(), request.getEmail());
        response.setIsOk(true);
        return response;
    }
}
