package CarsAdsApp.controller;

import CarsAdsApp.model.Ad;
import CarsAdsApp.model.dto.AdDTO;
import CarsAdsApp.service.AdService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class AdController {

    @Autowired
    private AdService adService;

    // Kreiranje ad-a
    @PostMapping(value = "/api/ad", produces = "application/json", consumes = "application/json")
    //@PreAuthorize("hasAuthority('CREATE_AD')")
    public ResponseEntity<String> createAd(@RequestBody AdDTO adDTO, Principal user) throws JSONException {

        if (adService.createAd(adDTO, user.getName()) == 200)
            return ResponseEntity.ok("Ad successfully created!");
        else if (adService.createAd(adDTO, user.getName()) == 402)
            return ResponseEntity.status(402).body("You have already created 3 ads!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }

    // Provera da li je ad userov
    @PostMapping(value = "/api/ad/check", produces = "application/json", consumes = "application/json")
    public Boolean createAd(@RequestBody JSONObject object, Principal user) throws JSONException {

        if (adService.checkAds(object, user.getName()))
            return true;
        else
            return false;
    }

    // Activate ad-a
    @PutMapping(value = "/api/ad/activate/{id}", produces = "application/json", consumes = "application/json")
    //@PreAuthorize("hasAuthority('ACTIVATE_AD')")
    public ResponseEntity<String> activateAd(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (adService.activateAd(id, user.getName()) == 200)
            return ResponseEntity.ok("Ad successfully deactivated!");
        else if (adService.activateAd(id, user.getName()) == 402)
            return ResponseEntity.status(402).body("You have already 3 ads!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }

    // Deactivate ad-a
    @DeleteMapping(value = "/api/ad/deactivate/{id}", produces = "application/json", consumes = "application/json")
    //@PreAuthorize("hasAuthority('DEACTIVATE_AD')")
    public ResponseEntity<String> deactivateAd(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (adService.deactivateAd(id, user.getName()))
            return ResponseEntity.ok("Ad successfully deactivated!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }


    // Izmena ad-a
    @PutMapping(value = "/api/ad/{id}", produces = "application/json", consumes = "application/json")
    //@PreAuthorize("hasAuthority('EDIT_AD')")
    public ResponseEntity<String> editAd(@PathVariable(value = "id") Long id, @RequestBody AdDTO adDTO, Principal user) throws JSONException {

        if (adService.editAd(id, adDTO, user.getName()))
            return ResponseEntity.ok("Ad successfully edited!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }

    @GetMapping(value = "/api/ads")
    public ResponseEntity<List<Ad>> getAll(){
        List<Ad> ads = adService.getAll();
        return ResponseEntity.ok(ads);
    }
}
