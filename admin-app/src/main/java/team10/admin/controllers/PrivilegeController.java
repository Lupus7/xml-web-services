package team10.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.admin.models.dto.PrivilegeDTO;
import team10.admin.models.dto.UserPrivilegeDTO;
import team10.admin.services.PrivilegeService;

import java.util.List;

@RestController
@RequestMapping("privilege")
public class PrivilegeController {

    @Autowired
    PrivilegeService privilegeService;

    @GetMapping("")
    public ResponseEntity<List<UserPrivilegeDTO>> getAllUserPrivileges() {
        List<UserPrivilegeDTO> retVal = privilegeService.getAllUserPrivileges();

        if (retVal != null) return ResponseEntity.ok(retVal);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<PrivilegeDTO>> getPrivileges(@PathVariable("email") String email) {
        List<PrivilegeDTO> retVal = privilegeService.getByUser(email);

        if (retVal != null) return ResponseEntity.ok(retVal);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/available/{email}")
    public ResponseEntity<List<PrivilegeDTO>> getAvailablePrivileges(@PathVariable("email") String email) {
        List<PrivilegeDTO> retVal = privilegeService.getAvailableByUser(email);

        if (retVal != null) return ResponseEntity.ok(retVal);
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updatePrivilege(@PathVariable("email") String email, @RequestBody PrivilegeDTO privilegeDTO) {
        if (privilegeService.updatePrivilege(email, privilegeDTO))
            return ResponseEntity.ok("Privilege '" + privilegeDTO.getName() + "' updated for user '" + email + "'");
        return ResponseEntity.badRequest().body("Invalid request");
    }

}
