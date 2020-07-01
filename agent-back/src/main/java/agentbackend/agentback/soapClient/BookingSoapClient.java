package agentbackend.agentback.soapClient;

import agentbackend.agentback.config.SoapProperties;
import agentbackend.agentback.controller.dto.BundleDTO;
import agentbackend.agentback.model.Ad;
import agentbackend.agentback.repository.AdRepository;
import com.car_rent.agent_api.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingSoapClient extends WebServiceGatewaySupport {

    @Autowired
    AdRepository adRepository;

    public ReserveBookingResponse reserveBooking(BundleDTO bundleDTO, String email) {

        ReserveBookingRequest request = new ObjectFactory().createReserveBookingRequest();
        BundleDetails bundleDetails = new BundleDetails();
        bundleDetails.setLoaner(bundleDTO.getLoaner());
        bundleDetails.getBooksDetails().addAll(bundleDTO.getBooks().stream().map(bookDTO -> {
            BookDetails bookDetails = new BookDetails();
            Optional<Ad> ad = adRepository.findById(bookDTO.getAdId());
            if(ad.isPresent())
                bookDetails.setAdId(ad.get().getServiceId());
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

    // Bundle

    public AcceptBundleResponse acceptBundle(Long id, String email) {

        AcceptBundleRequest request = new ObjectFactory().createAcceptBundleRequest();
        request.setId(id);
        request.setEmail(email);

        return (AcceptBundleResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.BOOKING_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/acceptBundleRequest"));
    }

    public RejectBundleResponse rejectBundle(Long id, String email) {

        RejectBundleRequest request = new ObjectFactory().createRejectBundleRequest();
        request.setId(id);
        request.setEmail(email);

        return (RejectBundleResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SoapProperties.BOOKING_WSDL, request,
                        new SoapActionCallback(SoapProperties.NAMESPACE_URI + "/rejectBundleRequest"));
    }

}
