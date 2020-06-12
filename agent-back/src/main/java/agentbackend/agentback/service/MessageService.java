package agentbackend.agentback.service;


import agentbackend.agentback.controller.dto.MessageDto;
import agentbackend.agentback.model.Message;
import agentbackend.agentback.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Boolean createNew(MessageDto msg){
        Message message = new Message();
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
