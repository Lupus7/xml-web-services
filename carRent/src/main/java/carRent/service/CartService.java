package carRent.service;

import carRent.model.Cart;
import carRent.model.dto.AdClientDTO;
import carRent.proxy.CarsAdsProxy;
import carRent.proxy.UserProxy;
import carRent.repository.CartRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    UserProxy userProxy;

    @Autowired
    CarsAdsProxy carsAdsProxy;

    public boolean addAdToCart(Long id, String email) throws JSONException {
        // provera da li user sa name postoji

        ResponseEntity<Long> userIdRes = userProxy.getUserId(email);
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        Long userId = userIdRes.getBody();

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return false;

        ResponseEntity<Boolean> check = carsAdsProxy.getAdById(id, email + ";MASTER");

        if (check == null || check.getBody() == null || !check.getBody())
            return false;

        ResponseEntity<Long> ownerId = carsAdsProxy.getOwnerById(id, email + ";MASTER");

        if (ownerId == null || ownerId.getBody() == null || ownerId.getBody().longValue() == userId)
            return false;

        cart.get().getAds().add(id);
        cartRepo.save(cart.get());

        return true;
    }

    public boolean deleteAdToCart(Long id, String email) throws JSONException {
        // provera da li user sa name postoji

        ResponseEntity<Long> userIdRes = userProxy.getUserId(email);
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        Long userId = userIdRes.getBody();

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return false;

        ResponseEntity<Boolean> check = carsAdsProxy.getAdById(id, email + ";MASTER");

        if (check == null || check.getBody() == null | !check.getBody())
            return false;

        cart.get().getAds().remove(id);
        cartRepo.save(cart.get());

        return true;
    }

    public ArrayList<AdClientDTO> getAllAds(String email) {
        // provera da li user sa name postoji
        ArrayList<AdClientDTO> list = new ArrayList<>();

        ResponseEntity<Long> userIdRes = userProxy.getUserId(email);
        if (userIdRes == null || userIdRes.getBody() == null)
            return list;

        Long userId = userIdRes.getBody();

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return list;

        for (Long id : cart.get().getAds()) {
            ResponseEntity<AdClientDTO> ad = carsAdsProxy.getAd(id, email+";MASTER");
            if (ad != null && ad.getBody() != null)
                list.add(ad.getBody());

        }

        return list;
    }

    public AdClientDTO getAdFromCart(Long id, String email) {

        ResponseEntity<Long> userIdRes = userProxy.getUserId(email);
        if (userIdRes == null || userIdRes.getBody() == null)
            return new AdClientDTO();

        Long userId = userIdRes.getBody();

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return new AdClientDTO();

        ResponseEntity<AdClientDTO> ad = carsAdsProxy.getAd(id, email+";MASTER");
        if (ad != null && ad.getBody() != null)
            return ad.getBody();

        return new AdClientDTO();

    }

    public boolean createCart(String email) {

        ResponseEntity<Long> userIdRes = userProxy.getUserId(email);
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        Long userId = userIdRes.getBody();

        Cart cart = new Cart(userId);
        cartRepo.save(cart);

        return true;
    }

}
