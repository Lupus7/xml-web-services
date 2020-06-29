package agentbackend.agentback.controller;

import agentbackend.agentback.service.BundleService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class BundleController {

    @Autowired
    BundleService bundleService;

    // Prihvatanje bundlea kod clienta
    @PutMapping(value = "/api/bundle/{id}")
    public ResponseEntity<String> acceptBundleRequest(@PathVariable(value = "id") Long id, Principal user) {

        if (bundleService.acceptBundleRequest(id, user.getName()))
            return ResponseEntity.ok("Bundle request reserved!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }


    // Odbijanje bundle requesta od strane vlasnika auta
    @DeleteMapping(value = "/api/bundle/reject/{id}")
    public ResponseEntity<String> rejectBundleRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bundleService.rejectBundleRequest(id, user.getName()))
            return ResponseEntity.ok("Bundle request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

}
