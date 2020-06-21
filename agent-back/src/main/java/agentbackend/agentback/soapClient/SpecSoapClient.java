package agentbackend.agentback.soapClient;

import agentbackend.agentback.config.SoapProperties;
import com.car_rent.agent_api.wsdl.GetAllSpecsRequest;
import com.car_rent.agent_api.wsdl.GetAllSpecsResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class SpecSoapClient extends WebServiceGatewaySupport {

    public GetAllSpecsResponse getAllSpecs(String email) {
        GetAllSpecsRequest request = new GetAllSpecsRequest();
        request.setEmail(email);

        return (GetAllSpecsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.SPEC_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/getAllSpecsRequest"));
    }
}
