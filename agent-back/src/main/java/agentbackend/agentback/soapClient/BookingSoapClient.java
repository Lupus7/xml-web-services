package agentbackend.agentback.soapClient;

import agentbackend.agentback.config.SoapProperties;
import agentbackend.agentback.controller.dto.BundleDTO;
import com.car_rent.agent_api.wsdl.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.stream.Collectors;

public class BookingSoapClient extends WebServiceGatewaySupport {

    public ReserveBookingResponse reserveBooking(BundleDTO bundleDTO, String email) {

        ReserveBookingRequest request = new ObjectFactory().createReserveBookingRequest();
        BundleDetails bundleDetails = new BundleDetails();
        bundleDetails.setLoaner(bundleDTO.getLoaner());
        bundleDetails.getBooksDetails().addAll(bundleDTO.getBooks().stream().map(bookDTO -> {
            BookDetails bookDetails = new BookDetails();
            bookDetails.setAdId(bookDTO.getAdId());
            try {
                bookDetails.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(bookDTO.getEndDate().toString()+":00"));
                bookDetails.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(bookDTO.getStartDate().toString()+":00"));

            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
            bookDetails.setPlace(bookDTO.getPlace());

            return bookDetails;
        }).collect(Collectors.toList()));


        request.setBundleDetails(bundleDetails);
        request.setEmail(email);


        return (ReserveBookingResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.BOOKING_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/reserveBookingRequest"));
    }

    public AcceptBookingResponse acceptBooking(Long id, String email) {

        AcceptBookingRequest request = new ObjectFactory().createAcceptBookingRequest();
        request.setId(id);
        request.setEmail(email);

        return (AcceptBookingResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.BOOKING_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/acceptBookingRequest"));
    }

    public CheckingBookingResponse checkingBooking(String object, String email) {

        CheckingBookingRequest request = new ObjectFactory().createCheckingBookingRequest();
        request.setObject(object);
        request.setEmail(email);

        return (CheckingBookingResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.BOOKING_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/checkingBookingRequest"));
    }

    public DeleteBookingResponse deleteBooking(String id, String email) {

       DeleteBookingRequest request = new ObjectFactory().createDeleteBookingRequest();
        request.setId(id);
        request.setEmail(email);

        return (DeleteBookingResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.BOOKING_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/deleteBookingRequest"));
    }

    public RejectBookingResponse rejectBooking(Long id, String email) {

        RejectBookingRequest request = new ObjectFactory().createRejectBookingRequest();
        request.setId(id);
        request.setEmail(email);

        return (RejectBookingResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.BOOKING_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/rejectBookingRequest"));
    }

    public GetBookingsResponse getBookings(String email) {

        GetBookingsRequest request = new ObjectFactory().createGetBookingsRequest();
        request.setEmail(email);

        return (GetBookingsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.BOOKING_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/getBookingsRequest"));
    }

}
