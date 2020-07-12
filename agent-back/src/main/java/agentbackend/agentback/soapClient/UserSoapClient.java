package agentbackend.agentback.soapClient;

import agentbackend.agentback.config.SoapProperties;
import com.car_rent.agent_api.wsdl.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class UserSoapClient extends WebServiceGatewaySupport {

    public GetAllUsersResponse getAllUsersResponse() {
        GetAllUsersRequest request = new ObjectFactory().createGetAllUsersRequest();
        request.setEmail("sta god");

        return (GetAllUsersResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.USER_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/getAllUsersRequest"));
    }
}
