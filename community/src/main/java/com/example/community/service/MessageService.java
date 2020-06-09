package com.example.community.service;

import com.example.community.controller.dto.MessageDto;
import com.example.community.model.Message;
import com.example.community.model.ObjectFactory;
import com.example.community.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Boolean createNew(MessageDto msg){
        ObjectFactory objectFactory = new ObjectFactory();
        Message message = objectFactory.createMessage();
        message.setBody(msg.getBody());
        message.setBooking(msg.getBooking());
        message.setDate(msg.getDate());
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
