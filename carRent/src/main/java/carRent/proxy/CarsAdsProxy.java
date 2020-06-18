package carRent.proxy;

import carRent.model.dto.AdClientDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cars-ads")
public interface CarsAdsProxy {

    @GetMapping(value = "/api/ad/client")
    ResponseEntity<List<AdClientDTO>> getClientAds(@RequestHeader("Authorization") String auth);

    @GetMapping(value = "/ad/check/{id}")
    ResponseEntity<Boolean> getAdById(@PathVariable("id") Long id, @RequestHeader("Authorization") String auth);

    @GetMapping(value = "/ad/owner/{id}")
    ResponseEntity<Long> getOwnerById(@PathVariable("id") Long id, @RequestHeader("Authorization") String auth);

    @DeleteMapping(value = "/api/ad/deactivate/{id}")
    ResponseEntity<String> deactivateAd(@PathVariable(value = "id") Long id, @RequestHeader("Authorization") String auth);

    @PostMapping(value = "/api/ad/check", produces = "application/json", consumes = "application/json")
    Boolean checking(@RequestBody JSONObject object, @RequestHeader("Authorization") String auth) throws JSONException;

    @GetMapping(value = "/api/ad/{id}")
    ResponseEntity<AdClientDTO> getAd(@PathVariable("id") Long id, @RequestHeader("Authorization") String auth);
}
