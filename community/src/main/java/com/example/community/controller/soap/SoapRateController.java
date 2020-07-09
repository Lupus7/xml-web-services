package com.example.community.controller.soap;

import com.car_rent.agent_api.GetRatesRequest;
import com.car_rent.agent_api.GetRatesResponse;
import com.car_rent.agent_api.ObjectFactory;
import com.car_rent.agent_api.RateDetails;
import com.example.community.controller.dto.RateDto;
import com.example.community.service.RateService;
import com.example.community.soap.SoapProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class SoapRateController {
    @Autowired
    RateService rateService;

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "getRatesRequest")
    @ResponsePayload
    public GetRatesResponse getRates(@RequestPayload GetRatesRequest request) {
        GetRatesResponse response = new ObjectFactory().createGetRatesResponse();

        List<RateDto> rates = rateService.getRatesOnly(request.getUser());

        response.getRates().addAll(
                rates
                        .stream()
                        .map(rate -> {
                            RateDetails details = new ObjectFactory().createRateDetails();
                            details.setId(rate.getId());
                            details.setApproved(rate.isApproved());
                            details.setBooking(rate.getBooking());
                            details.setCarId(rate.getCarId());
                            details.setComment(rate.getComment());
                            details.setRate(rate.getRate());
                            details.setRater(rate.getUser());
                            return details;
                        })
                        .collect(Collectors.toList())
        );

        return response;
    }
}
