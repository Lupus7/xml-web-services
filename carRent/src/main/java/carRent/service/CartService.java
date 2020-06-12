package carRent.service;

import carRent.model.Cart;
import carRent.model.dto.AdClientDTO;
import carRent.repository.CartRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        Long userId = userIdRes.getBody();

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return false;

        ResponseEntity<Boolean> check = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/ad/check/" + id,
                HttpMethod.GET, entity, Boolean.class, new Object());

        if (check == null || check.getBody() == null || !check.getBody())
            return false;

        ResponseEntity<Long> ownerId = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/ad/owner/" + id, HttpMethod.GET, entity,
                Long.class, new Object());

        if (ownerId == null || ownerId.getBody() == null || ownerId.getBody().longValue() == userId)
            return false;

        cart.get().getAds().add(id);
        cartRepo.save(cart.get());

        return true;
    }

    public boolean deleteAdToCart(Long id, String email) throws JSONException {
        // provera da li user sa name postoji

        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return false;

        Long userId = userIdRes.getBody();

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return false;

        ResponseEntity<Boolean> check = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/ad/check/" + id,
                HttpMethod.GET, entity, Boolean.class, new Object());

        if (check == null || check.getBody() == null | !check.getBody())
            return false;

        cart.get().getAds().remove(id);
        cartRepo.save(cart.get());

        return true;
    }

    public ArrayList<AdClientDTO> getAllAds(String email) {
        // provera da li user sa name postoji
        ArrayList<AdClientDTO> list = new ArrayList<>();
        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return list;

        Long userId = userIdRes.getBody();

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return list;

        for (Long id : cart.get().getAds()) {
            ResponseEntity<AdClientDTO> ad = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/api/ad/" + id,
                    HttpMethod.GET, entity, AdClientDTO.class, new Object());
            if (ad != null && ad.getBody() != null)
                list.add(ad.getBody());

        }

        return list;
    }

    public AdClientDTO getAdFromCart(Long id, String email) {

        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        String carsAdsServiceIp = discoveryClient.getInstances("cars-ads").get(0).getHost();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "NONE;MASTER");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Long> userIdRes = new RestTemplate().exchange("http://" + userServiceIp + ":8080/client-control/user/" + email, HttpMethod.GET, entity, Long.class, new Object());
        if (userIdRes == null || userIdRes.getBody() == null)
            return new AdClientDTO();

        Long userId = userIdRes.getBody();

        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (!cart.isPresent())
            return new AdClientDTO();

        ResponseEntity<AdClientDTO> ad = new RestTemplate().exchange("http://" + carsAdsServiceIp + ":8080/api/ad/" + id,
                HttpMethod.GET, entity, AdClientDTO.class, new Object());
        if (ad != null && ad.getBody() != null)
            return ad.getBody();

        return new AdClientDTO();

    }

    public boolean createCart(String email) {
        String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
        Long userId = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/client-control/user/" + email, Long.class);
        if (userId == null)
            return false;

        Cart cart = new Cart(userId);
        cartRepo.save(cart);

        return true;
    }

}
