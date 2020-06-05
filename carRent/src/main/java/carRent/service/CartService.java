package carRent.service;

import carRent.model.Cart;
import carRent.model.dto.AdClientDTO;
import carRent.repository.CartRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private DiscoveryClient discoveryClient;

    public boolean addAdToCart(Long id, String email) throws JSONException {
        // provera da li user sa name postoji

        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();

        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null)
            return false;

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return false;

        Boolean check = new RestTemplate().getForObject("http://" + carsAdsServiceIp + ":8080/ad/check/" + id,
                Boolean.class);

        if (check == null || !check)
            return false;

        cart.get().getAds().add(id);
        cartRepo.save(cart.get());

        return true;
    }

    public boolean deleteAdToCart(Long id, String email) throws JSONException {
        // provera da li user sa name postoji

        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();

        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null)
            return false;

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return false;

        Boolean check = new RestTemplate().getForObject("http://" + carsAdsServiceIp + ":8080/ad/check/" + id,
                Boolean.class);

        if (check == null || !check)
            return false;

        cart.get().getAds().remove(id);
        cartRepo.save(cart.get());

        return true;
    }

    public ArrayList<AdClientDTO> getAllAds(String email) {
        // provera da li user sa name postoji
        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();

        ArrayList<AdClientDTO> list = new ArrayList<>();
        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null)
            return list;

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return list;

        for (Long id : cart.get().getAds()) {
            AdClientDTO ad = new RestTemplate().getForObject("http://" + carsAdsServiceIp + ":8080/api/ad/" + id,
                    AdClientDTO.class);
            if (ad != null)
                list.add(ad);

        }

        return list;
    }
}
