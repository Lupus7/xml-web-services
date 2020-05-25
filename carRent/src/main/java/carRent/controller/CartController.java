package carRent.controller;

import carRent.model.dto.AdDTO;
import carRent.service.CartService;
import org.json.JSONException;
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

    // Oglas se dodaje u cart
    @PutMapping(value = "/api/cart/{id}", produces = "application/json", consumes = "application/json")
    //@PreAuthorize("hasAuthority('UPDATE_CART')")
    public ResponseEntity<String> addAdToCart(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (cartService.addAdToCart(id, user.getName()))
            return ResponseEntity.ok("Ad successfully added to cart!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Oglas se brise iz carta
    @DeleteMapping(value = "/api/cart/{id}", produces = "application/json", consumes = "application/json")
    //@PreAuthorize("hasAuthority('DELETE_FROM_CART')")
    public ResponseEntity<String> deleteAdToCart(@PathVariable(value = "id") Long id, Principal user) throws JSONException {

        if (cartService.deleteAdToCart(id, user.getName()))
            return ResponseEntity.ok("Ad successfully removed from cart!");
        else
            return ResponseEntity.status(400).body("Could not accept");
    }

    // Svi oglasi iz carta
    @GetMapping(value = "/api/cart", produces = "application/json", consumes = "application/json")
    //@PreAuthorize("hasAuthority('READ_CART')")
    public ResponseEntity<ArrayList<AdDTO>> getCart(Principal user) throws JSONException {

        return ResponseEntity.ok((ArrayList<AdDTO>) cartService.getAllAds(user.getName()));
    }


}
