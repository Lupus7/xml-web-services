package com.example.community.service;

import com.example.community.controller.dto.ConversationDTO;
import com.example.community.controller.dto.MessageDto;
import com.example.community.model.Message;
import com.example.community.model.ObjectFactory;
import com.example.community.proxy.CarRentProxy;
import com.example.community.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    CarRentProxy carRentProxy;

    public MessageDto createNew(MessageDto msg, String user) {

        ObjectFactory objectFactory = new ObjectFactory();
        Message message = objectFactory.createMessage();
        message.setBody(msg.getBody());
        message.setDate(LocalDateTime.now());
        message.setReceiver(msg.getReceiver());
        message.setSender(user);

        messageRepository.save(message);

        MessageDto dto = new MessageDto();
        dto.setBody(message.getBody());
        dto.setDate(message.getDate());
        dto.setReceiver(message.getReceiver());
        dto.setSender(message.getSender());
        dto.setId(message.getId());

        return dto;
    }

    public ArrayList<MessageDto> getAll() {
        ArrayList<Message> messages = (ArrayList<Message>) messageRepository.findAll();
        ArrayList<MessageDto> msgs = new ArrayList<>();
        for (Message m : messages) {
            MessageDto dto = new MessageDto();
            dto.setBody(m.getBody());
            dto.setBooking(m.getBooking());
            dto.setDate(m.getDate());
            msgs.add(dto);
        }
        return msgs;
    }

    public Long startConversation(ConversationDTO conversationDTO, String user) {

        /*Boolean check = carRentProxy.checkingBookingRequests(conversationDTO.getBookingId().toString(), user.getName() + ";MASTER").getBody();
        if (!check)
            return false;*/
        //Get Ad id, and call ad service to get loaner name
        ObjectFactory objectFactory = new ObjectFactory();
        Message message = objectFactory.createMessage();
        message.setBody("BEGIN");
        message.setBooking(conversationDTO.getBookingId());
        message.setDate(LocalDateTime.now());
        message.setSender(user);
        message.setReceiver(conversationDTO.getReceiver());

        messageRepository.save(message);
        return message.getId();
    }

    public ArrayList<String> getPeople(String user) {

        HashMap<String, String> people = new HashMap<>();
        ArrayList<String> peopleList = new ArrayList<>();

        List<Message> messageListSenders = messageRepository.findAllBySender(user);
        List<Message> messageListReceivers = messageRepository.findAllByReceiver(user);
        for (Message mess : messageListSenders) {
            if (people.containsKey(mess.getReceiver()))
                continue;
            else
                people.put(mess.getReceiver(), mess.getReceiver());
        }

        for (Message mess : messageListReceivers) {
            if (people.containsKey(mess.getSender()))
                continue;
            else
                people.put(mess.getSender(), mess.getSender());
        }

        for (Map.Entry<String, String> entry : people.entrySet()) {
            peopleList.add(entry.getKey());
        }

        return peopleList;

    }

    public ArrayList<MessageDto> getConversation(String receiver, String user) {
        ArrayList<MessageDto> messageDtos = new ArrayList<>();
        List<Message> messageList1 = messageRepository.findAllBySenderAndReceiverOrderByDateDesc(user, receiver);
        List<Message> messageList2 = messageRepository.findAllByReceiverAndSenderOrderByDateDesc(user, receiver);

        for (Message m : messageList1) {
            MessageDto dto = new MessageDto();
            dto.setBody(m.getBody());
            dto.setBooking(m.getBooking());
            dto.setDate(m.getDate());
            dto.setReceiver(m.getReceiver());
            dto.setSender(m.getSender());
            dto.setId(m.getId());
            messageDtos.add(dto);
        }

        for (Message m : messageList2) {
            MessageDto dto = new MessageDto();
            dto.setBody(m.getBody());
            dto.setBooking(m.getBooking());
            dto.setDate(m.getDate());
            dto.setReceiver(m.getReceiver());
            dto.setSender(m.getSender());
            dto.setId(m.getId());
            messageDtos.add(dto);
        }
        MessageDto temp;
        for (int i = 0; i < messageDtos.size(); i++) {
            for (int j = 0; j < messageDtos.size(); j++) {
                if (messageDtos.get(i).getDate().isBefore(messageDtos.get(j).getDate())) {
                    temp = messageDtos.get(j);
                    messageDtos.set(j, messageDtos.get(i));
                    messageDtos.set(i, temp);
                }
            }
        }

        return messageDtos;


    }
}
