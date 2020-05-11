package team10.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.admin.models.dto.ClientDTO;
import team10.admin.models.dto.NewAgentDTO;
import team10.admin.models.dto.NewCompanyDTO;
import team10.admin.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("client-control")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> retVal = clientService.getAll();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/block/{email}")
    public ResponseEntity<String> blockClient(@PathVariable("email") String email) {
        if (clientService.block(email))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body("Invalid request");
    }

    @PutMapping("/activate/{email}")
    public ResponseEntity<String> activateClient(@PathVariable("email") String email) {
        if (clientService.activate(email))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body("Invalid request");
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteClient(@PathVariable("email") String email) {
        if (clientService.delete(email))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body("Invalid request");
    }

    @PostMapping("/company")
    public ResponseEntity<String> registerCompany(@RequestBody NewCompanyDTO newCompanyDTO) {
        if (clientService.registerCompany(newCompanyDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/agent")
    public ResponseEntity<String> registerAgent(@RequestBody NewAgentDTO newAgentDTO) {
        if (clientService.registerAgent(newAgentDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }
}
