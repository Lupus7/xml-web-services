package agentbackend.agentback.soapClient;

import agentbackend.agentback.config.SoapProperties;
import agentbackend.agentback.controller.dto.ReportDto;
import agentbackend.agentback.model.Message;
import com.car_rent.agent_api.wsdl.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public class CommunitySoapClient extends WebServiceGatewaySupport {

    public CreateMessageResponse createMessage(Message message, String email) {
        CreateMessageRequest request = new ObjectFactory().createCreateMessageRequest();

        request.setUser(email);

        MessageDetails details = new ObjectFactory().createMessageDetails();
        details.setBody(message.getBody());
        details.setBooking(message.getBooking());

        GregorianCalendar gcal = GregorianCalendar.from(message.getDate().atZone(ZoneId.systemDefault()));
        try {
            details.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal));
        } catch (DatatypeConfigurationException e) {
            System.out.println("CANNOT CONVERT DATE: " + message.getDate());
            return null;
        }

        details.setId(message.getId());
        details.setReceiver(message.getReceiver());
        details.setSender(email);

        request.setMessageDetails(details);

        return (CreateMessageResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.COMM_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/createMessageRequest"));
    }

    public StartConversationResponse startConversation(long bookingId, String sender, String receiver) {
        StartConversationRequest request = new ObjectFactory().createStartConversationRequest();

        request.setUser(sender);
        request.setReceiver(receiver);
        request.setBookingId(bookingId);

        return (StartConversationResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.COMM_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/startConversationRequest"));
    }

    public GetConversationResponse getConversation(String sender, String receiver) {
        GetConversationRequest request = new ObjectFactory().createGetConversationRequest();

        request.setUser(sender);
        request.setReceiver(receiver);

        return (GetConversationResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.COMM_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/getConversationRequest"));
    }

    public GetPeopleResponse getPeople(String user) {
        GetPeopleRequest request = new ObjectFactory().createGetPeopleRequest();

        request.setUser(user);

        return (GetPeopleResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.COMM_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/getPeopleRequest"));
    }

    public GetRatesResponse getRates(String user) {
        GetRatesRequest request = new ObjectFactory().createGetRatesRequest();

        request.setUser(user);

        return (GetRatesResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.COMM_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/getRatesRequest"));
    }

    public CreateReportResponse createReport(ReportDto reportDto, String user) {
        CreateReportRequest request = new ObjectFactory().createCreateReportRequest();

        request.setUser(user);
        ReportDetails details = new ObjectFactory().createReportDetails();

        details.setBooking(reportDto.getBooking());
        details.setExtraInfo(reportDto.getExtraInfo());
        details.setId(-1L);
        details.setMileage(reportDto.getAllowedMileage());

        request.setReport(details);

        return (CreateReportResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.COMM_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/createReportRequest"));
    }

    public GetReportsResponse getReports(String user) {
        GetReportsRequest request = new ObjectFactory().createGetReportsRequest();

        request.setUser(user);

        return (GetReportsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.COMM_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/getReportsRequest"));
    }
}
