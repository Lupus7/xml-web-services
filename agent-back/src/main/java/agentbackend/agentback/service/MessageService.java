package agentbackend.agentback.service;


import agentbackend.agentback.controller.dto.ConversationDTO;
import agentbackend.agentback.controller.dto.MessageDto;
import agentbackend.agentback.controller.dto.MessageDto2;
import agentbackend.agentback.model.*;
import agentbackend.agentback.repository.AdRepository;
import agentbackend.agentback.repository.BookingRepository;
import agentbackend.agentback.repository.MessageRepository;
import agentbackend.agentback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AdRepository adRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;

    public Boolean createNew(MessageDto msg, Principal user){
        Message message = new Message();
        message.setBody(msg.getBody());
        //Proveri da li je booking sa tim IDijem prihvacen
        Booking booking = bookingRepository.getOne(msg.getBooking());
        if(!(booking.getState() == RequestState.RESERVED || booking.getState() == RequestState.PAID))
            return false;

        //Ako Oglas sa tim id-em ne postoji, ne moze
        Ad ad = adRepository.getOne(booking.getAd());
        if(ad == null)
            return false;

        //Uzmi usera koji je kreirao oglas, on je receiver
        User receiver = userRepository.getOne(ad.getOwnerId());
        if(receiver == null)
            return false;
        message.setReceiver(receiver.getEmail());

        //Proveri da li user koji je trenutno ulogovan, je onaj koji je poslao booking request, ako nije ne moze da chatuje
        User loaner = userRepository.getOne(booking.getLoaner());
        if(!loaner.getEmail().equals(user.getName()))
            return false;

        message.setBooking(msg.getBooking());
        message.setDate(LocalDateTime.now());
        message.setSender(loaner.getEmail());
        messageRepository.save(message);
        return true;
    }

    public ArrayList<MessageDto> getAll(){
        ArrayList<Message> messages = (ArrayList<Message>) messageRepository.findAll();
        ArrayList<MessageDto> msgs = new ArrayList<>();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        for(Message m: messages){
            MessageDto dto = new MessageDto();
            dto.setBody(m.getBody());
            dto.setBooking(m.getBooking());
            dto.setDate(formatter.format(m.getDate()));
            dto.setReceiver(m.getReceiver());
            dto.setSender(m.getSender());
            msgs.add(dto);
        }
        return msgs;
    }

    public ArrayList<MessageDto> getUsersSentMessages(Principal user){
        ArrayList<Message> messages = (ArrayList<Message>) messageRepository.findAll();
        ArrayList<MessageDto> msgs = new ArrayList<>();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        for(Message m: messages){
            if(m.getSender().equals(user.getName())) {
                MessageDto dto = new MessageDto();
                dto.setBody(m.getBody());
                dto.setBooking(m.getBooking());
                dto.setDate(formatter.format(m.getDate()));
                dto.setReceiver(m.getReceiver());
                dto.setSender(m.getSender());
                msgs.add(dto);
            }
        }
        return msgs;
    }

    public ArrayList<MessageDto> getUsersReceived(Principal user){
        ArrayList<Message> messages = (ArrayList<Message>) messageRepository.findAll();
        ArrayList<MessageDto> msgs = new ArrayList<>();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        for(Message m: messages){
            if(m.getReceiver().equals(user.getName())) {
                MessageDto dto = new MessageDto();
                dto.setBody(m.getBody());
                dto.setBooking(m.getBooking());
                dto.setDate(formatter.format(m.getDate()));
                dto.setReceiver(m.getReceiver());
                dto.setSender(m.getSender());
                msgs.add(dto);
            }
        }
        return msgs;
    }

    public ArrayList<MessageDto> getUsersWholeInbox(Principal user){
        ArrayList<Message> messages = (ArrayList<Message>) messageRepository.findAll();
        ArrayList<MessageDto> msgs = new ArrayList<>();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        for(Message m: messages){
            if(m.getReceiver().equals(user.getName()) || m.getSender().equals(user.getName())) {
                MessageDto dto = new MessageDto();
                dto.setBody(m.getBody());
                dto.setBooking(m.getBooking());
                dto.setDate(formatter.format(m.getDate()));
                dto.setReceiver(m.getReceiver());
                dto.setSender(m.getSender());
                msgs.add(dto);
            }
        }
        return msgs;
    }

    public boolean reply(MessageDto messageDto, Principal user) {
        Message replyMess = new Message();
        replyMess.setBody(messageDto.getBody());
        replyMess.setReceiver(messageDto.getReceiver());
        replyMess.setSender(user.getName());
        replyMess.setDate(LocalDateTime.now());
        replyMess.setBooking(messageDto.getBooking());
        messageRepository.save(replyMess);
        return true;
    }

    public ArrayList<String> getPeople(Principal user) {

        HashMap<String, String> people = new HashMap<>();
        ArrayList<String> peopleList = new ArrayList<>();

        List<Message> messageListSenders = messageRepository.findAllBySender(user.getName());
        List<Message> messageListReceivers = messageRepository.findAllByReceiver(user.getName());
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

    public ArrayList<MessageDto2> getConversation(String receiver, Principal user) {
        ArrayList<MessageDto2> messageDtos = new ArrayList<>();
        List<Message> messageList1 = messageRepository.findAllBySenderAndReceiverOrderByDateDesc(user.getName(), receiver);
        List<Message> messageList2 = messageRepository.findAllByReceiverAndSenderOrderByDateDesc(user.getName(), receiver);

        for (Message m : messageList1) {
            MessageDto2 dto = new MessageDto2();
            dto.setBody(m.getBody());
            dto.setBooking(m.getBooking());
            dto.setDate(m.getDate());
            dto.setReceiver(m.getReceiver());
            dto.setSender(m.getSender());
            dto.setId(m.getId());
            messageDtos.add(dto);
        }

        for (Message m : messageList2) {
            MessageDto2 dto = new MessageDto2();
            dto.setBody(m.getBody());
            dto.setBooking(m.getBooking());
            dto.setDate(m.getDate());
            dto.setReceiver(m.getReceiver());
            dto.setSender(m.getSender());
            dto.setId(m.getId());
            messageDtos.add(dto);
        }
        MessageDto2 temp;
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

    public MessageDto2 createNew(MessageDto2 msg, Principal user) {

        Message message = new Message();
        message.setBody(msg.getBody());
        message.setDate(LocalDateTime.now());
        message.setReceiver(msg.getReceiver());
        message.setSender(user.getName());

        messageRepository.save(message);

        MessageDto2 dto = new MessageDto2();
        dto.setBody(message.getBody());
        dto.setDate(message.getDate());
        dto.setReceiver(message.getReceiver());
        dto.setSender(message.getSender());
        dto.setId(message.getId());

        return dto;
    }

    public boolean startConversation(ConversationDTO conversationDTO, Principal user) {
        Message message = new Message();
        message.setBody("BEGIN");
        message.setBooking(conversationDTO.getBookingId());
        message.setDate(LocalDateTime.now());
        message.setSender(user.getName());
        message.setReceiver(conversationDTO.getReceiver());

        messageRepository.save(message);
        return true;
    }

}
