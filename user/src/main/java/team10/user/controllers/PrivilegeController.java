package team10.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.user.models.dto.PrivilegeDTO;
import team10.user.models.dto.UserPrivilegeDTO;
import team10.user.services.PrivilegeService;

import java.util.List;

@RestController
@RequestMapping("privilege")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @GetMapping("")
    //@PreAuthorize("hasAuthority('READ_USER_PRIVILEGES')")
    public ResponseEntity<List<UserPrivilegeDTO>> getAllUserPrivileges() {
        List<UserPrivilegeDTO> retVal = privilegeService.getAllUserPrivileges();

        if (retVal != null) return ResponseEntity.ok(retVal);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/{email}")
    //@PreAuthorize("hasAuthority('READ_USER_PRIVILEGES')")
    public ResponseEntity<List<PrivilegeDTO>> getPrivileges(@PathVariable("email") String email) {
        List<PrivilegeDTO> retVal = privilegeService.getByUser(email);

        if (retVal != null) return ResponseEntity.ok(retVal);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/available/{email}")
    //@PreAuthorize("hasAuthority('READ_USER_PRIVILEGES')")
    public ResponseEntity<List<PrivilegeDTO>> getAvailablePrivileges(@PathVariable("email") String email) {
        List<PrivilegeDTO> retVal = privilegeService.getAvailableByUser(email);

        if (retVal != null) return ResponseEntity.ok(retVal);
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/{email}")
    //@PreAuthorize("hasAuthority('UPDATE_USER_PRIVILEGES')")
    public ResponseEntity<String> updatePrivilege(@PathVariable("email") String email, @RequestBody PrivilegeDTO privilegeDTO) {
        if (privilegeService.updatePrivilege(email, privilegeDTO))
            return ResponseEntity.ok("Privilege '" + privilegeDTO.getName() + "' updated for user '" + email + "'");
        return ResponseEntity.badRequest().body("Invalid request");
    }

}
