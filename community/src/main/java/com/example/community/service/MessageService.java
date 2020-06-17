package com.example.community.service;

import com.example.community.controller.dto.MessageDto;
import com.example.community.model.Message;
import com.example.community.model.ObjectFactory;
import com.example.community.proxy.CarRentProxy;
import com.example.community.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    CarRentProxy carRentProxy;

    public Boolean createNew(MessageDto msg, Principal user){
        Integer bookingId = (int) (msg.getBooking());
        Boolean check = carRentProxy.checkingBookingRequests(bookingId.toString(),user.getName() + ";MASTER").getBody();
        if(!check)
            return false;
        //Get Ad id, and call ad service to get loaner name
        Long adId = carRentProxy.getBookingsAd(msg.getBooking(), user.getName() + ";MASTER").getBody();
        ObjectFactory objectFactory = new ObjectFactory();
        Message message = objectFactory.createMessage();
        message.setBody(msg.getBody());
        message.setBooking(msg.getBooking());
        message.setDate(msg.getDate());
        //Message sender and receiver

        messageRepository.save(message);
        return true;
    }

    public ArrayList<MessageDto> getAll(){
        ArrayList<Message> messages = (ArrayList<Message>) messageRepository.findAll();
        ArrayList<MessageDto> msgs = new ArrayList<>();
        for(Message m: messages){
            MessageDto dto = new MessageDto();
            dto.setBody(m.getBody());
            dto.setBooking(m.getBooking());
            dto.setDate(m.getDate());
            msgs.add(dto);
        }
        return msgs;
    }
}
