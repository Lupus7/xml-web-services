package CarsAdsApp.service;

import CarsAdsApp.model.Ad;
import CarsAdsApp.model.Car;
import CarsAdsApp.model.Image;
import CarsAdsApp.model.dto.AdClientDTO;
import CarsAdsApp.model.dto.AdDTO;
import CarsAdsApp.proxy.RentProxy;
import CarsAdsApp.proxy.UserProxy;
import CarsAdsApp.repository.AdRepository;
import CarsAdsApp.repository.CarRepository;
import CarsAdsApp.repository.ImageRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AdService {

    @Autowired
    private AdRepository adRepo;

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    UserProxy userProxy;

    @Autowired
    RentProxy rentProxy;

    public int createAd(AdDTO adDTO, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return 400;
        Long userId = userIdResponse.getBody();

        ResponseEntity<String> userRoleResponse = userProxy.getUserRole(email);
        if (userRoleResponse == null || userRoleResponse.getStatusCode() != HttpStatus.OK)
            return 400;

        // provera za 3 ad-a
        boolean active = true;

        if (userRoleResponse.getBody().equals("ROLE_CLIENT")) {
            ArrayList<Ad> ads = adRepo.findAllByOwnerIdAndActive(userId, true);
            if (ads.size() == 3)
                active = false;
        }

        if (adDTO == null)
            return 400;
        if (adDTO.getPlace() == null || adDTO.getStartDate() == null || adDTO.getEndDate() == null || adDTO.getCarId() == null)
            return 400;
        if (adDTO.getPlace().equals("") || adDTO.getStartDate().equals("") || adDTO.getEndDate().equals(""))
            return 400;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Ad ad = new Ad(LocalDateTime.parse(adDTO.getStartDate(), formatter), LocalDateTime.parse(adDTO.getEndDate(), formatter), adDTO.getPlace(), adDTO.getCarId(), userId, active);
        adRepo.save(ad);

        return 200;
    }


    public boolean checkAds(String obj, String email) throws JSONException {
        System.out.println(obj);
        JSONObject object = new JSONObject(obj);
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Long userId = userIdResponse.getBody();

        if (object == null || object.get("array") == null)
            return false;

        JSONArray array = (JSONArray) object.get("array");

        for (int i = 0; i < array.length(); i++) {
            Long id = array.getLong(i);
            Optional<Ad> ad = adRepo.findById(id);
            if (!ad.isPresent() || ad.get().getOwnerId() != userId)
                return false;
        }


        return true;
    }

    public int activateAd(Long id, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return 400;

        ResponseEntity<String> userRoleResponse = userProxy.getUserRole(email);
        if (userRoleResponse == null || userRoleResponse.getStatusCode() != HttpStatus.OK)
            return 400;

        Long userId = userIdResponse.getBody();

        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent() || ad.get().getOwnerId() != userId)
            return 400;

        if (userRoleResponse.getBody().equals("ROLE_CLIENT")) {

            ArrayList<Ad> ads = adRepo.findAllByOwnerIdAndActive(userId, true);
            if (ads.size() == 3)
                return 402;
        }

        ResponseEntity<Boolean> check = rentProxy.checkState(id, email + ";MASTER");
        if (check == null || !check.getBody())
            return 405;

        ad.get().setActive(true);
        adRepo.save(ad.get());

        return 200;
    }

    public boolean deactivateAd(Long id, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Long userId = userIdResponse.getBody();

        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent() || ad.get().getOwnerId() != userId)
            return false;

        ad.get().setActive(false);
        adRepo.save(ad.get());

        return true;
    }

    public boolean editAd(Long id, AdDTO adDTO, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Long userId = userIdResponse.getBody();

        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent() || ad.get().getOwnerId() != userId)
            return false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (adDTO.getStartDate() != null) {
            ad.get().setStartDate(LocalDateTime.parse(adDTO.getStartDate(), formatter));
        }

        if (adDTO.getEndDate() != null) {
            ad.get().setEndDate(LocalDateTime.parse(adDTO.getEndDate(), formatter));
        }

        if (adDTO.getPlace() != null)
            ad.get().setPlace(adDTO.getPlace());

        adRepo.save(ad.get());

        return true;
    }

    // bojanovo
    public List<Ad> getAll() {
        List<Ad> ads = adRepo.findAll();
        return ads;
    }

    public List<AdClientDTO> getClientAds(String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return new ArrayList<>();

        Long userId = userIdResponse.getBody();
        List<AdClientDTO> adClientDTOS = new ArrayList<>();

        List<Ad> ads = adRepo.findAllByOwnerId(userId);

        for (Ad ad : ads) {
            Optional<Car> car = carRepo.findById(ad.getCarId());
            if (car.isPresent()) {
                List<Image> images = imageRepo.findAllByCarId(car.get().getId());
                if (images == null)
                    images = new ArrayList<>();
                AdClientDTO adClientDTO = new AdClientDTO(ad, car.get(), images);
                adClientDTOS.add(adClientDTO);
            }
        }


        return adClientDTOS;
    }

    public AdClientDTO getAdById(Long id) {
        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent())
            return null;
        Optional<Car> car = carRepo.findById(ad.get().getCarId());
        if (!car.isPresent())
            return null;
        List<Image> images = imageRepo.findAllByCarId(car.get().getId());
        if (images == null)
            return null;
        // TODO: ADD ADVERTIZER
        return new AdClientDTO(ad.get(), car.get(), images);
    }

    public Boolean getCheckAd(Long id) {
        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent())
            return false;
        return true;
    }

    public Long getOwnerId(Long id) {
        Optional<Ad> ad = adRepo.findById(id);
        if (ad.isPresent())
            return ad.get().getOwnerId();
        return null;
    }

    public Boolean getStatus(Long id) {
        Optional<Ad> ad = adRepo.findById(id);
        if (ad.isPresent())
            return ad.get().isActive();
        return null;
    }


    ///// SOAP


}
