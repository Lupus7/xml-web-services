package carRent.controller;

import carRent.model.dto.AdClientDTO;
import carRent.service.CartService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    // Pravljenje carta
    @PostMapping(value = "/api/cart")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<String> createCart(@RequestBody JSONObject temp, Principal user) throws JSONException {

        if (cartService.createCart(user.getName()))
            return ResponseEntity.ok("Cart created!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }


    // Oglas se dodaje u cart
    @PutMapping(value = "/api/cart/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('UPDATE_CART')")
    public ResponseEntity<String> addAdToCart(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (cartService.addAdToCart(id, user.getName()))
            return ResponseEntity.ok("Ad successfully added to cart!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Oglas se brise iz carta
    @DeleteMapping(value = "/api/cart/{id}")
    @PreAuthorize("hasAuthority('DELETE_FROM_CART')")
    public ResponseEntity<String> deleteAdToCart(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (cartService.deleteAdToCart(id, user.getName()))
            return ResponseEntity.ok("Ad successfully removed from cart!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Svi oglasi iz carta
    @GetMapping(value = "/api/cart", produces = "application/json")
    @PreAuthorize("hasAuthority('READ_CART')")
    public ResponseEntity<ArrayList<AdClientDTO>> getCart(Principal user) throws JSONException {

        return ResponseEntity.ok((ArrayList<AdClientDTO>) cartService.getAllAds(user.getName()));
    }

    // Jedan oglas iz karta
    @GetMapping(value = "/api/cart/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('READ_CART')")
    public ResponseEntity<AdClientDTO> getAdFromCart(@PathVariable("id") Long id, Principal user) throws JSONException {

        return ResponseEntity.ok((AdClientDTO) cartService.getAdFromCart(id,user.getName()));
    }


}
