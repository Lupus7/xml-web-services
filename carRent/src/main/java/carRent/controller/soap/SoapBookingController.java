package carRent.controller.soap;

import carRent.model.Booking;
import carRent.model.dto.BookingDTO;
import carRent.service.BookingService;
import carRent.soap.SoapProperties;
import com.car_rent.agent_api.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.HashMap;
import java.util.Set;

@Endpoint
public class SoapBookingController {

    @Autowired
    BookingService bookingService;

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "reserveBookingRequest")
    @ResponsePayload
    public ReserveBookingResponse reserveBooking(@RequestPayload ReserveBookingRequest request) throws JSONException {
        ReserveBookingResponse response = new ReserveBookingResponse();
        HashMap<Long, Booking> bookings = bookingService.reserveBookingRequest(bookingService.mappingDto(request.getBundleDetails()), request.getEmail());
        response.setId(bookings.values().stream().findFirst().get().getId());
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "acceptBookingRequest")
    @ResponsePayload
    public AcceptBookingResponse acceptBooking(@RequestPayload AcceptBookingRequest request) throws JSONException {
        AcceptBookingResponse response = new AcceptBookingResponse();
        bookingService.acceptBookingRequest(request.getId(), request.getEmail());
        response.setResponse("Booking accepted!");
        return response;
    }


    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "checkingBookingRequest")
    @ResponsePayload
    public CheckingBookingResponse checkingBooking(@RequestPayload CheckingBookingRequest request) {
        CheckingBookingResponse response = new CheckingBookingResponse();
        boolean check = bookingService.checkingBookingRequests(request.getObject(), request.getEmail());
        response.setResponse(check);
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "deleteBookingRequest")
    @ResponsePayload
    public DeleteBookingResponse deleteBooking(@RequestPayload DeleteBookingRequest request) {
        DeleteBookingResponse response = new DeleteBookingResponse();
        bookingService.deleteCarsBookings(request.getId(), request.getEmail());
        response.setResponse("Booking request canceled!");
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "rejectBookingRequest")
    @ResponsePayload
    public RejectBookingResponse rejectBooking(@RequestPayload RejectBookingRequest request) throws JSONException {
        RejectBookingResponse response = new RejectBookingResponse();
        bookingService.rejectBookingRequest(request.getId(), request.getEmail());
        response.setResponse("Booking request rejected!");
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "getBookingsRequest")
    @ResponsePayload
    public GetBookingsResponse getBooking(@RequestPayload GetBookingsRequest request) throws DatatypeConfigurationException {
        GetBookingsResponse response = new GetBookingsResponse();
        Set<BookingDTO> bookings = bookingService.getAllBookingRequestsFromOthers(request.getEmail());
        Set<BookingDetails> bookingDetails = bookingService.mappingDtoArray(bookings);
        response.getResponse().addAll(bookingDetails);
        return response;
    }


}
