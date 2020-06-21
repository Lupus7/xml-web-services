package agentbackend.agentback.controller;


import agentbackend.agentback.controller.dto.ConversationDTO;
import agentbackend.agentback.controller.dto.MessageDto;
import agentbackend.agentback.controller.dto.MessageDto2;
import agentbackend.agentback.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

//    @PostMapping("/messages")
//    public ResponseEntity<String> createMessage(@RequestBody MessageDto messageDto, Principal user){
//        if(messageService.createNew(messageDto, user)){
//            return ResponseEntity.ok("Successfully created");
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }
//
//    @GetMapping("/messages")
//    public ResponseEntity<ArrayList<MessageDto>> findAll(){
//        ArrayList<MessageDto> msgs = messageService.getAll();
//        return ResponseEntity.ok(msgs);
//    }
//
//    @GetMapping("messages/inbox")
//    public ResponseEntity<ArrayList<MessageDto>> inbox(Principal user){
//        ArrayList<MessageDto> msgs = messageService.getUsersWholeInbox(user);
//        return ResponseEntity.ok(msgs);
//    }
//
//    @GetMapping("/messages/sent")
//    public ResponseEntity<ArrayList<MessageDto>> sentMessages(Principal user){
//        ArrayList<MessageDto> msgs = messageService.getUsersSentMessages(user);
//        return ResponseEntity.ok(msgs);
//    }
//
//    @GetMapping("/messages/received")
//    public ResponseEntity<ArrayList<MessageDto>> receivedMessages(Principal user){
//        ArrayList<MessageDto> msgs = messageService.getUsersReceived(user);
//        return ResponseEntity.ok(msgs);
//    }
//
//    @PostMapping("/messages/reply")
//    public ResponseEntity<String> reply(@RequestBody MessageDto messageDto, Principal user){
//        if(messageService.reply(messageDto, user)){
//            return ResponseEntity.ok("Successfully created");
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }

    @PostMapping("/message")
    public ResponseEntity<MessageDto2> createMessage(@RequestBody MessageDto2 messageDto, Principal user){
        return ResponseEntity.ok(messageService.createNew(messageDto,user));
    }

    @PostMapping("/message/conversation")
    public ResponseEntity<String> startConversation(@RequestBody ConversationDTO conversationDTO, Principal user){
        if(messageService.startConversation(conversationDTO, user)){
            return ResponseEntity.ok("Successfully started");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/message/people")
    public ResponseEntity<ArrayList<String>> findReceivers(Principal user){
        ArrayList<String> receivers = messageService.getPeople(user);
        return ResponseEntity.ok(receivers);
    }

    @GetMapping("/message/{receiver}")
    public ResponseEntity<ArrayList<MessageDto2>> getConversation(@PathVariable("receiver") String receiver, Principal user){
        ArrayList<MessageDto2> msgs = messageService.getConversation(receiver,user);
        return ResponseEntity.ok(msgs);
    }
}

