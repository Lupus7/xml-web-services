package CarsAdsApp.controller.soap;

import CarsAdsApp.model.dto.AdClientDTO;
import CarsAdsApp.model.dto.AdDTO;
import CarsAdsApp.service.soap.SoapAdService;
import CarsAdsApp.soap.SoapProperties;
import com.car_rent.agent_api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.HashMap;
import java.util.List;

@Endpoint
public class SoapAdController {

    @Autowired
    SoapAdService adService;

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "createAdRequest")
    @ResponsePayload
    public CreateAdResponse createAd(@RequestPayload CreateAdRequest request) {
        CreateAdResponse response = new ObjectFactory().createCreateAdResponse();
        AdDTO adDTO = new AdDTO();
        adDTO.setCarId(request.getAdDetails().getCarId());
        adDTO.setEndDate(request.getAdDetails().getEndDate());
        adDTO.setStartDate(request.getAdDetails().getStartDate());
        adDTO.setPlace(request.getAdDetails().getPlace());
        Long id = adService.createAdSoap(adDTO,request.getEmail());
        response.setId(id);
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "activateAdRequest")
    @ResponsePayload
    public ActivateAdResponse activateAd(@RequestPayload ActivateAdRequest request) {
        ActivateAdResponse response = new ObjectFactory().createActivateAdResponse();

        HashMap<Long,Boolean> responseService = adService.activateAdSoap(request.getId(),request.getEmail());
        response.setId(responseService.keySet().stream().findFirst().get());
        response.setActive(responseService.values().stream().findFirst().get());
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "deactivateAdRequest")
    @ResponsePayload
    public DeactivateAdResponse deactivateAd(@RequestPayload DeactivateAdRequest request) {
        DeactivateAdResponse response = new ObjectFactory().createDeactivateAdResponse();

        HashMap<Long,Boolean> responseService = adService.deactivateAdSoap(request.getId(),request.getEmail());
        response.setId(responseService.keySet().stream().findFirst().get());
        response.setActive(responseService.values().stream().findFirst().get());
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "editAdRequest")
    @ResponsePayload
    public EditAdResponse editAd(@RequestPayload EditAdRequest request) {
        EditAdResponse response = new ObjectFactory().createEditAdResponse();
        Long id  = adService.editAd(request.getId(), request.getAdDetails(), request.getEmail());
        response.setId(id);
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "getAdsRequest")
    @ResponsePayload
    public GetAdsResponse editAd(@RequestPayload GetAdsRequest request) throws DatatypeConfigurationException {
        GetAdsResponse response = new ObjectFactory().createGetAdsResponse();
        List<AdDetails> ads  = adService.getAds(request.getEmail());
        response.getAdDetails().addAll(ads);
        return response;
    }
}
