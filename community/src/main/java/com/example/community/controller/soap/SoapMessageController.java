package com.example.community.controller.soap;

import com.car_rent.agent_api.*;
import com.example.community.controller.dto.ConversationDTO;
import com.example.community.controller.dto.MessageDto;
import com.example.community.service.MessageService;
import com.example.community.soap.SoapProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class SoapMessageController {
    @Autowired
    MessageService messageService;

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "createMessageRequest")
    @ResponsePayload
    public CreateMessageResponse createMessage(@RequestPayload CreateMessageRequest request) {
        CreateMessageResponse response = new ObjectFactory().createCreateMessageResponse();

        MessageDto messageDto = new MessageDto();
        messageDto.setBody(request.getMessageDetails().getBody());
        messageDto.setBooking(request.getMessageDetails().getBooking());
        messageDto.setDate(request.getMessageDetails().getDate().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
        messageDto.setReceiver(request.getMessageDetails().getReceiver());
        messageDto.setSender(request.getMessageDetails().getSender());

        MessageDto serviceResponse = messageService.createNew(messageDto, request.getUser());

        response.setMessageDetails(request.getMessageDetails());
        if (serviceResponse.getId() == null)
            serviceResponse.setId(-1L);
        response.getMessageDetails().setId(serviceResponse.getId());

        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "startConversationRequest")
    @ResponsePayload
    public StartConversationResponse startConversation(@RequestPayload StartConversationRequest request) {
        StartConversationResponse response = new ObjectFactory().createStartConversationResponse();

        ConversationDTO conversationDTO = new ConversationDTO();
        conversationDTO.setBookingId(request.getBookingId());
        conversationDTO.setReceiver(request.getReceiver());

        Long id = messageService.startConversation(conversationDTO, request.getUser());

        if (id == null)
            id = -1L;

        response.setId(id);

        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "getConversationRequest")
    @ResponsePayload
    public GetConversationResponse getConversation(@RequestPayload GetConversationRequest request) {
        GetConversationResponse response = new ObjectFactory().createGetConversationResponse();

        List<MessageDto> messages = messageService.getConversation(request.getReceiver(), request.getUser());

        response.getMessages().addAll(
                messages
                        .stream()
                        .map(message -> {
                            MessageDetails details = new ObjectFactory().createMessageDetails();
                            details.setId(message.getId());
                            GregorianCalendar gcal = GregorianCalendar.from(message.getDate().atZone(ZoneId.systemDefault()));
                            try {
                                details.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal));
                            } catch (DatatypeConfigurationException e) {
                                System.out.println("CANNOT CONVERT DATE: " + message.getDate());
                            }
                            details.setBooking(message.getBooking());
                            details.setBody(message.getBody());
                            details.setReceiver(message.getReceiver());
                            details.setSender(message.getSender());

                            return details;
                        })
                        .collect(Collectors.toList())
        );

        return response;
    }

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "getPeopleRequest")
    @ResponsePayload
    public GetPeopleResponse getPeople(@RequestPayload GetPeopleRequest request) {
        GetPeopleResponse response = new ObjectFactory().createGetPeopleResponse();

        List<String> messages = messageService.getPeople(request.getUser());

        response.getPeople().addAll(messages);

        return response;
    }
}
