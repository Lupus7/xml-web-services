package team10.user.controllers.soap;

import com.car_rent.agent_api.GetAllUsersRequest;
import com.car_rent.agent_api.GetAllUsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import team10.user.services.UserUtilityService;
import team10.user.soap.SoapProperties;

@Endpoint
public class UserSoapController {
    @Autowired
    UserUtilityService userUtilityService;

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest request) {
        GetAllUsersResponse response = userUtilityService.getAllSoapUserDetails();
        return response;
    }
}
