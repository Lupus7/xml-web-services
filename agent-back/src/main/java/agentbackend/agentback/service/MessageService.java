package agentbackend.agentback.service;


import agentbackend.agentback.controller.dto.MessageDto;
import agentbackend.agentback.model.*;
import agentbackend.agentback.repository.AdRepository;
import agentbackend.agentback.repository.BookingRepository;
import agentbackend.agentback.repository.MessageRepository;
import agentbackend.agentback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        message.setReveicer(receiver.getEmail());

        //Proveri da li user koji je trenutno ulogovan, je onaj koji je poslao booking request, ako nije ne moze da chatuje
        User loaner = userRepository.getOne(booking.getLoaner());
        if(!loaner.getEmail().equals(user.getName()))
            return false;

        message.setBooking(msg.getBooking());
        message.setDate(new Date());
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
            dto.setReceiver(m.getReveicer());
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
                dto.setReceiver(m.getReveicer());
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
            if(m.getReveicer().equals(user.getName())) {
                MessageDto dto = new MessageDto();
                dto.setBody(m.getBody());
                dto.setBooking(m.getBooking());
                dto.setDate(formatter.format(m.getDate()));
                dto.setReceiver(m.getReveicer());
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
            if(m.getReveicer().equals(user.getName()) || m.getSender().equals(user.getName())) {
                MessageDto dto = new MessageDto();
                dto.setBody(m.getBody());
                dto.setBooking(m.getBooking());
                dto.setDate(formatter.format(m.getDate()));
                dto.setReceiver(m.getReveicer());
                dto.setSender(m.getSender());
                msgs.add(dto);
            }
        }
        return msgs;
    }

    public boolean reply(MessageDto messageDto, Principal user) {
        Message replyMess = new Message();
        replyMess.setBody(messageDto.getBody());
        replyMess.setReveicer(messageDto.getReceiver());
        replyMess.setSender(user.getName());
        replyMess.setDate(new Date());
        replyMess.setBooking(messageDto.getBooking());
        messageRepository.save(replyMess);
        return true;
    }
}
