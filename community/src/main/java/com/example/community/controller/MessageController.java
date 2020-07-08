package com.example.community.controller;


import com.example.community.controller.dto.ConversationDTO;
import com.example.community.controller.dto.MessageDto;
import com.example.community.service.MessageService;
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

    @PostMapping("/message")
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDto, Principal user){
        return ResponseEntity.ok(messageService.createNew(messageDto,user.getName()));
    }

    @PostMapping("/message/conversation")
    public ResponseEntity<String> startConversation(@RequestBody ConversationDTO conversationDTO, Principal user){
        if(messageService.startConversation(conversationDTO, user.getName()) != null){
            return ResponseEntity.ok("Successfully started");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/message/people")
    public ResponseEntity<ArrayList<String>> findReceivers(Principal user){
        ArrayList<String> receivers = messageService.getPeople(user.getName());
        return ResponseEntity.ok(receivers);
    }

    @GetMapping("/message/{receiver}")
    public ResponseEntity<ArrayList<MessageDto>> getConversation(@PathVariable("receiver") String receiver, Principal user){
        ArrayList<MessageDto> msgs = messageService.getConversation(receiver,user.getName());
        return ResponseEntity.ok(msgs);
    }


}
