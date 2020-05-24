package carRent.service;

import carRent.model.Cart;
import carRent.model.User;
import carRent.model.dto.AdDTO;
import carRent.repository.CartRepository;
import carRent.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepo;

    public boolean addAdToCart(Long id, String name) throws JSONException {
        // provera da li user sa name postoji

        User user = userRepo.findByEmail(name);
        if (user == null)
            return false;

        Optional<Cart> cart = cartRepo.findById(user.getCart());
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

    public boolean deleteAdToCart(Long id, String name) throws JSONException {
        // provera da li user sa name postoji

        User user = userRepo.findByEmail(name);
        if (user == null)
            return false;

        Optional<Cart> cart = cartRepo.findById(user.getCart());
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

    public ArrayList<AdDTO> getAllAds(String name) {
        // provera da li user sa name postoji
        ArrayList<AdDTO> list = new ArrayList<>();
        User user = userRepo.findByEmail(name);
        if (user == null)
            return list;

        Optional<Cart> cart = cartRepo.findById(user.getCart());
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
