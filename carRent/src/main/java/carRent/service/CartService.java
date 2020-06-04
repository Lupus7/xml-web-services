package carRent.service;

import carRent.model.Cart;
import carRent.model.dto.AdDTO;
import carRent.repository.CartRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    public boolean addAdToCart(Long id, String email) throws JSONException {
        // provera da li user sa name postoji

        Long userId = new RestTemplate().getForObject("http://localhost:8080/user/client-control/user/"+email, Long.class);
        if (userId == null)
            return false;

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return false;

        AdDTO ad = new RestTemplate().getForObject("http://localhost:8080/cars/api/ads/" + id,
                AdDTO.class);

        if (ad == null)
            return false;

        cart.get().getAds().add(ad.getId());
        cartRepo.save(cart.get());

        return true;
    }

    public boolean deleteAdToCart(Long id, String email) throws JSONException {
        // provera da li user sa name postoji

        Long userId = new RestTemplate().getForObject("http://localhost:8080/user/client-control/user/"+email, Long.class);
        if (userId == null)
            return false;

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return false;

        AdDTO ad = new RestTemplate().getForObject("http://localhost:8080/cars/api/ads/" + id,
                AdDTO.class);
        if (ad == null)
            return false;

        cart.get().getAds().remove(ad.getId());
        cartRepo.save(cart.get());

        return true;
    }

    public ArrayList<AdDTO> getAllAds(String email) {
        // provera da li user sa name postoji
        ArrayList<AdDTO> list = new ArrayList<>();
        Long userId = new RestTemplate().getForObject("http://localhost:8080/user/client-control/user/"+email, Long.class);
        if (userId == null)
            return list;

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return list;

        for (Long id : cart.get().getAds()) {
            AdDTO ad = new RestTemplate().getForObject("http://localhost:8080/cars/api/ads/" + id,
                    AdDTO.class);
            if (ad != null)
                list.add(new AdDTO(ad.getId(), ad.getStartDate(), ad.getEndDate(), ad.getPlace(), ad.getCarId()));

        }

        return list;
    }
}
