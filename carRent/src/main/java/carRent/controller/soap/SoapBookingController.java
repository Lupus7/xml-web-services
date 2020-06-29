package carRent.controller.soap;

import carRent.model.Booking;
import carRent.model.dto.BookingDTO;
import carRent.service.BookingService;
import carRent.service.BundleService;
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

    @Autowired
    BundleService bundleService;

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "reserveBookingRequest")
    @ResponsePayload
    public ReserveBookingResponse reserveBooking(@RequestPayload ReserveBookingRequest request) {
        ReserveBookingResponse response = new ReserveBookingResponse();
        HashMap<Long, Booking> bookings = bookingService.reserveBookingRequest(bookingService.mappingDto(request.getBundleDetails()), request.getEmail());
        if (bookings.size() == 1) {
            response.setBundleId((long) -1);
            response.getBookings().add(bookings.values().stream().findFirst().get().getId());
        } else if (bookings.size() > 1) {
            response.setBundleId(bookings.values().stream().findFirst().get().getBundle().getId());
            for (Booking b : bookings.values())
                response.getBookings().add(b.getId());
        }

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
    public CheckingBookingResponse checkingBooking(@RequestPayload CheckingBookingRequest request) throws JSONException {
        CheckingBookingResponse response = new CheckingBookingResponse();
        boolean check = bookingService.checkingBookingRequests(request.getObject(), request.getEmail());
        response.setResponse(check);
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "deleteBookingRequest")
    @ResponsePayload
    public DeleteBookingResponse deleteBooking(@RequestPayload DeleteBookingRequest request) throws JSONException {
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
    public GetBookingsResponse getBookings(@RequestPayload GetBookingsRequest request) throws DatatypeConfigurationException {
        GetBookingsResponse response = new GetBookingsResponse();
        Set<BookingDTO> bookings = bookingService.getAllReceivedBookingRequests(request.getEmail());
        Set<BookingDetails> bookingDetails = bookingService.mappingDtoArray(bookings);
        response.getResponse().addAll(bookingDetails);
        return response;
    }

    // za bundle

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "acceptBundleRequest")
    @ResponsePayload
    public AcceptBundleResponse acceptBundle(@RequestPayload AcceptBundleRequest request) {
        AcceptBundleResponse response = new AcceptBundleResponse();
        bundleService.acceptBundleRequest(request.getId(), request.getEmail());
        response.setResponse("Bundle request accepted!");
        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "rejectBundleRequest")
    @ResponsePayload
    public RejectBundleResponse rejectBundle(@RequestPayload RejectBundleRequest request) {
        RejectBundleResponse response = new RejectBundleResponse();
        bundleService.rejectBundleRequest(request.getId(), request.getEmail());
        response.setResponse("Bundle request rejected!");
        return response;
    }


}
