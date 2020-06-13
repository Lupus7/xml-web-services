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

import java.util.ArrayList;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<String> createMessage(@RequestBody MessageDto messageDto){
        if(messageService.createNew(messageDto)){
            return ResponseEntity.ok("Successfully created");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/messages")
    public ResponseEntity<ArrayList<MessageDto>> findAll(){
        ArrayList<MessageDto> msgs = messageService.getAll();
        return ResponseEntity.ok(msgs);
    }

}
