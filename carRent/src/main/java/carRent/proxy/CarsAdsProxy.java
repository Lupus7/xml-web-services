package carRent.proxy;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cars-ads")
public interface CarsAdsProxy {

    @GetMapping(value = "/ad/check/{id}")
    ResponseEntity<Boolean> getAdById(@PathVariable("id") Long id);

    @GetMapping(value = "/ad/owner/{id}")
    ResponseEntity<Long> getOwnerById(@PathVariable("id") Long id);

    @DeleteMapping(value = "/api/ad/deactivate/{id}")
    ResponseEntity<String> deactivateAd(@PathVariable(value = "id") Long id, @RequestHeader("Authorization") String auth);

    @PostMapping(value = "/api/ad/check", produces = "application/json", consumes = "application/json")
    Boolean checking(@RequestBody JSONObject object, @RequestHeader("Authorization") String auth) throws JSONException;
}
