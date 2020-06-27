package carRent.controller;

import carRent.service.BundleService;
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


    // Odbijanje bundle requesta od strane vlasnika auta
    @DeleteMapping(value = "/api/bundle/reject/{id}")
    @PreAuthorize("hasAuthority('REJECT_BOOKING')")
    public ResponseEntity<String> rejectBundleRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bundleService.rejectBundleRequest(id, user.getName()))
            return ResponseEntity.ok("Bundle request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Prihvatanje bundlea kod clienta
    @PutMapping(value = "/api/bundle/{id}")
    @PreAuthorize("hasAuthority('ACCEPT_BOOKING')")
    public ResponseEntity<String> acceptBundleRequest(@PathVariable(value = "id") Long id, Principal user) {

        if (bundleService.acceptBundleRequest(id, user.getName()))
            return ResponseEntity.ok("Bundle request reserved!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Otkazivanje bundle requesta od strane clienta koje je zatrazio booking
    @DeleteMapping(value = "/api/bundle/{id}")
    @PreAuthorize("hasAuthority('CANCEL_BOOKING')")
    public ResponseEntity<String> cancelBundleRequest(@PathVariable(value = "id") Long id, Principal user){

        if (bundleService.cancelBundleRequest(id, user.getName()))
            return ResponseEntity.ok("Bundle request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }
}
