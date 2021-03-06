package CarsAdsApp.controller;

import CarsAdsApp.model.Ad;
import CarsAdsApp.model.dto.AdClientDTO;
import CarsAdsApp.model.dto.AdDTO;
import CarsAdsApp.service.AdService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class AdController {

    @Autowired
    private AdService adService;

    // Kreiranje ad-a
    @PostMapping(value = "/api/ad", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('CREATE_AD')")
    public ResponseEntity<String> createAd(@RequestBody AdDTO adDTO, Principal user) throws JSONException {
        int response = adService.createAd(adDTO, user.getName());
        if (response == 200)
            return ResponseEntity.ok("Ad successfully created!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }

    // Provera da li je ad userov
    @PostMapping("/api/ad/check")
    @PreAuthorize("hasAuthority('MASTER')")
    public Boolean checking(@RequestBody String object, Principal user) throws JSONException {

        if (adService.checkAds(object, user.getName()))
            return true;
        else
            return false;
    }

    // Activate ad-a
    @PutMapping(value = "/api/ad/activate/{id}")
    @PreAuthorize("hasAuthority('ACTIVATE_AD')")
    public ResponseEntity<String> activateAd(@PathVariable(value = "id") Long id, Principal user) {
        int response = adService.activateAd(id, user.getName());
        if (response == 200)
            return ResponseEntity.ok("Ad successfully deactivated!");
        else if (response == 402)
            return ResponseEntity.status(402).body("You have already 3 ads!");
        else if (response == 405)
            return ResponseEntity.status(405).body("Ad aldready rented!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }

    // Deactivate ad-a
    @DeleteMapping(value = "/api/ad/deactivate/{id}")
    @PreAuthorize("hasAuthority('DEACTIVATE_AD') or hasAuthority('MASTER')")
    public ResponseEntity<String> deactivateAd(@PathVariable(value = "id") Long id, Principal user) {

        if (adService.deactivateAd(id, user.getName()))
            return ResponseEntity.ok("Ad successfully deactivated!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }


    // Izmena ad-a
    @PutMapping(value = "/api/ad/{id}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('EDIT_AD')")
    public ResponseEntity<String> editAd(@PathVariable(value = "id") Long id, @RequestBody AdDTO adDTO, Principal user){

        if (adService.editAd(id, adDTO,user.getName()))
            return ResponseEntity.ok("Ad successfully edited!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }

    // Svi adovi clienta
    @GetMapping(value = "/api/ad/client")
    @PreAuthorize("hasAuthority('READ_ADS') or hasAuthority('MASTER')")
    public ResponseEntity<List<AdClientDTO>> getClientAds(Principal user){
        List<AdClientDTO> ads = adService.getClientAds(user.getName());
        return ResponseEntity.ok(ads);
    }

    // Ad Active or Not?
    @GetMapping(value = "/api/ad/active/{id}")
    @PreAuthorize("hasAuthority('READ_ADS') or hasAuthority('MASTER')")
    public ResponseEntity<Boolean> getStatus(@PathVariable(value = "id") Long id){

        return ResponseEntity.ok(adService.getStatus(id));
    }

    @GetMapping(value = "/api/ad")
    @PreAuthorize("hasAuthority('READ_ADS') or hasAuthority('MASTER')")
    public ResponseEntity<List<Ad>> getAll(){
        List<Ad> ads = adService.getAll();
        return ResponseEntity.ok(ads);
    }

    @GetMapping(value = "/api/ad/{id}")
    @PreAuthorize("hasAuthority('READ_CLIENT_ADS') or hasAuthority('MASTER')")
    public ResponseEntity<AdClientDTO> getAd(@PathVariable("id") Long id){
        AdClientDTO ad = adService.getAdById(id);
        return ResponseEntity.ok(ad);
    }

    // Id Ad
    @GetMapping(value = "/ad/check/{id}")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<Boolean> getAdById(@PathVariable("id") Long id){
        return ResponseEntity.ok(adService.getCheckAd(id));
    }

    // Id Ownera
    @GetMapping(value = "/ad/owner/{id}")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<Long> getOwnerById(@PathVariable("id") Long id){
        return ResponseEntity.ok(adService.getOwnerId(id));
    }

    @GetMapping("/ad/{id}/car")
    public ResponseEntity<Long> getCarIdFromAd(@PathVariable("id") Long adId){
        Long carId = adService.getCarId(adId);
        return ResponseEntity.status(HttpStatus.OK).body(carId);
    }
}
