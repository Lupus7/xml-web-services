package carRent.controller;

import carRent.model.dto.GetBundleDTO;
import carRent.service.BundleService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
public class BundleController {

    @Autowired
    BundleService bundleService;

    // Prihvatanje bundlea kod clienta
    @PutMapping(value = "/api/bundle/{id}")
    @PreAuthorize("hasAuthority('ACCEPT_BOOKING')")
    public ResponseEntity<String> acceptBundleRequest(@PathVariable(value = "id") Long id, Principal user) {

        if (bundleService.acceptBundleRequest(id, user.getName()))
            return ResponseEntity.ok("Bundle request reserved!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }


    // Odbijanje bundle requesta od strane vlasnika auta
    @DeleteMapping(value = "/api/bundle/reject/{id}")
    @PreAuthorize("hasAuthority('REJECT_BOOKING')")
    public ResponseEntity<String> rejectBundleRequest(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (bundleService.rejectBundleRequest(id, user.getName()))
            return ResponseEntity.ok("Bundle request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Otkazivanje bundle requesta od strane clienta koje je zatrazio booking
    @DeleteMapping(value = "/api/bundle/{id}")
    @PreAuthorize("hasAuthority('CANCEL_BOOKING')")
    public ResponseEntity<String> cancelBundleRequest(@PathVariable(value = "id") Long id, Principal user) {

        if (bundleService.cancelBundleRequest(id, user.getName()))
            return ResponseEntity.ok("Bundle request canceled!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }


    //Get all bundle request client received
    @GetMapping(value = "/api/bundle/request", produces = "application/json")
    @PreAuthorize("hasAuthority('READ_BOOKINGS')")
    public ResponseEntity<Set<GetBundleDTO>> getAllReceivedBundleRequests(Principal user){
        return ResponseEntity.ok(bundleService.getAllReceivedBundleRequests(user.getName()));
    }

    // Get all bundle request client send
    @GetMapping(value = "/api/bundle", produces = "application/json")
    @PreAuthorize("hasAuthority('READ_BOOKINGS')")
    public ResponseEntity<List<GetBundleDTO>> getAllSentBundleRequests(Principal user){

        return ResponseEntity.ok(bundleService.getAllSentBundleRequests(user.getName()));

    }
}
