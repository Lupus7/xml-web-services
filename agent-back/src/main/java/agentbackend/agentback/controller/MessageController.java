package agentbackend.agentback.controller;


import agentbackend.agentback.controller.dto.MessageDto;
import agentbackend.agentback.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<String> createMessage(@RequestBody MessageDto messageDto, Principal user){
        if(messageService.createNew(messageDto, user)){
            return ResponseEntity.ok("Successfully created");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/messages")
    public ResponseEntity<ArrayList<MessageDto>> findAll(){
        ArrayList<MessageDto> msgs = messageService.getAll();
        return ResponseEntity.ok(msgs);
    }

    @GetMapping("messages/inbox")
    public ResponseEntity<ArrayList<MessageDto>> inbox(Principal user){
        ArrayList<MessageDto> msgs = messageService.getUsersWholeInbox(user);
        return ResponseEntity.ok(msgs);
    }

    @GetMapping("/messages/sent")
    public ResponseEntity<ArrayList<MessageDto>> sentMessages(Principal user){
        ArrayList<MessageDto> msgs = messageService.getUsersSentMessages(user);
        return ResponseEntity.ok(msgs);
    }

    @GetMapping("/messages/received")
    public ResponseEntity<ArrayList<MessageDto>> receivedMessages(Principal user){
        ArrayList<MessageDto> msgs = messageService.getUsersReceived(user);
        return ResponseEntity.ok(msgs);
    }

    @PostMapping("/messages/reply")
    public ResponseEntity<String> reply(@RequestBody MessageDto messageDto, Principal user){
        if(messageService.reply(messageDto, user)){
            return ResponseEntity.ok("Successfully created");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }



}

