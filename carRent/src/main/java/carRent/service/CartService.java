package carRent.service;

import carRent.model.Ad;
import carRent.model.Cart;
import carRent.model.User;
import carRent.model.dto.AdDTO;
import carRent.repository.AdRepository;
import carRent.repository.CartRepository;
import carRent.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private AdRepository adRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private UserRepository userRepo;


    public boolean addAdToCart(Long id, String name) throws JSONException {
        // provera da li user sa name postoji

        User user = userRepo.findByEmail(name);
        if (user == null)
            return false;

        Cart cart = user.getCart();
        Optional<Ad> ad = adRepo.findById(id);
        if(!ad.isPresent())
            return false;

        cart.getAds().add(ad.get());
        cartRepo.save(cart);

        return true;
    }

    public boolean deleteAdToCart(Long id, String name) throws JSONException {
        // provera da li user sa name postoji

        User user = userRepo.findByEmail(name);
        if (user == null)
            return false;

        Cart cart = user.getCart();
        Optional<Ad> ad = adRepo.findById(id);
        if(!ad.isPresent())
            return false;

        cart.getAds().remove(ad.get());
        cartRepo.save(cart);

        return true;
    }

    public ArrayList<AdDTO> getAllAds(String name) {
        // provera da li user sa name postoji
        ArrayList<AdDTO> list = new ArrayList<>();
        User user = userRepo.findByEmail(name);
        if (user == null)
            return list;
        for (Ad ad : user.getCart().getAds())
            list.add(new AdDTO(ad.getId(), ad.getStartDate(), ad.getEndDate(), ad.getPlace(), ad.getCar().getId()));

        return list;
    }
}
