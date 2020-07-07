package agentbackend.agentback.service;


import agentbackend.agentback.controller.dto.AdClientDTO;
import agentbackend.agentback.controller.dto.AdDTO;
import agentbackend.agentback.model.*;
import agentbackend.agentback.repository.*;
import agentbackend.agentback.soapClient.AdSoapClient;
import com.car_rent.agent_api.wsdl.ActivateAdResponse;
import com.car_rent.agent_api.wsdl.CreateAdResponse;
import com.car_rent.agent_api.wsdl.DeactivateAdResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private UserRepository userRepository;

    @Autowired
    private AdSoapClient adSoapClient;

    @Autowired
    BookingRepository bookingRepository;

    public int createAd(AdDTO adDTO, String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return 400;


        if (adDTO == null)
            return 400;
        if (adDTO.getPlace() == null || adDTO.getStartDate() == null || adDTO.getEndDate() == null || adDTO.getCarId() == null)
            return 400;
        if (adDTO.getPlace().equals("") || adDTO.getStartDate().equals("") || adDTO.getEndDate().equals(""))
            return 400;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Optional<Car> car = carRepo.findById(adDTO.getCarId());
        if (!car.isPresent())
            return 400;

        CreateAdResponse response = adSoapClient.createAd(adDTO, email);
        Ad ad = new Ad(LocalDateTime.parse(adDTO.getStartDate(), formatter), LocalDateTime.parse(adDTO.getEndDate(), formatter), adDTO.getPlace(), car.get(), user.getEmail());
        ad.setServiceId(response.getId());
        adRepo.save(ad);

        return 200;
    }

    public boolean checkAds(JSONObject object, String email) throws JSONException {

        // provera da li user sa name postoji, provera da li je ad userov
        Long userId = 0L;
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userId = user.getId();
        }
        if (object == null || object.get("array") == null)
            return false;

        JSONArray array = (JSONArray) object.get("array");

        for (int i = 0; i < array.length(); i++) {
            Long id = array.getLong(i);
            Optional<Ad> ad = adRepo.findById(id);
            User owner = userRepository.findByEmail(ad.get().getOwner());
            if (!ad.isPresent() || owner.getId() != userId)
                return false;
        }


        return true;
    }

    public boolean activateAd(Long id, String email) {

        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;

        Optional<Ad> ad = adRepo.findById(id);
        User owner = userRepository.findByEmail(ad.get().getOwner());

        if (!ad.isPresent() || !owner.getEmail().equals(user.getEmail()) || ad.get().isActive())
            return false;

        List<Booking> bookings = bookingRepository.findAllByAd(ad.get());
        for (Booking b : bookings) {
            if (b.getState().equals(RequestState.PAID) || b.getState().equals(RequestState.ENDED))
                return false;
        }


        ad.get().setActive(true);
        adRepo.save(ad.get());

        if (ad.get().getServiceId() != null) {
            ActivateAdResponse response = adSoapClient.activateAd(ad.get().getServiceId(), email);
            if (!response.isResponse())
                return false;
        }

        return true;
    }

    public boolean deactivateAd(Long id, String email) {

        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;

        Optional<Ad> ad = adRepo.findById(id);
        User owner = userRepository.findByEmail(ad.get().getOwner());
        if (!ad.isPresent() || !owner.getEmail().equals(user.getEmail()) || !ad.get().isActive())
            return false;

        ad.get().setActive(false);
        adRepo.save(ad.get());

        if (ad.get().getServiceId() != null) {
            DeactivateAdResponse response = adSoapClient.deactivateAd(ad.get().getServiceId(), email);
            if (!response.isResponse())
                return false;
        }


        return true;
    }

    public boolean editAd(Long id, AdDTO adDTO, String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;

        Optional<Ad> ad = adRepo.findById(id);
        User owner = userRepository.findByEmail(ad.get().getOwner());
        if (!ad.isPresent() || !owner.getEmail().equals(user.getEmail()) || !ad.get().isActive())
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

        if (ad.get().getServiceId() != null)
            adSoapClient.editAd(ad.get().getCar().getServiceId(), ad.get().getServiceId(), adDTO, email);

        return true;
    }

    // bojanovo
    public List<Ad> getAll() {
        List<Ad> ads = adRepo.findAll();
        return ads;
    }

    public List<AdClientDTO> getClientAds(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return new ArrayList<>();

        List<AdClientDTO> adClientDTOS = new ArrayList<>();

        List<Ad> ads = adRepo.findAllByOwner(user.getEmail());
        for (Ad ad : ads) {
            List<Image> images = imageRepo.findAllByCar(ad.getCar());
            if (images == null)
                images = new ArrayList<>();
            adClientDTOS.add(new AdClientDTO(ad, ad.getCar(), images));

        }
        return adClientDTOS;
    }

    public AdClientDTO getAdById(Long id) {
        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent())
            return null;
        Optional<Car> car = carRepo.findById(ad.get().getCar().getId());
        if (!car.isPresent())
            return null;
        List<Image> images = imageRepo.findAllByCarId(car.get().getId());
        if (images == null)
            return null;
        return new AdClientDTO(ad.get(), car.get(), images, car.get().getOwner());
    }

    public Boolean getCheckAd(Long id) {
        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent())
            return false;
        return true;
    }

    public Boolean getStatus(Long id) {
        Optional<Ad> ad = adRepo.findById(id);
        if (ad.isPresent())
            return ad.get().isActive();
        return null;
    }


    public Long getOwnerId(Long id) {
        Optional<Ad> ad = adRepo.findById(id);
        if (ad.isPresent()) {
            User owner = userRepository.findByEmail(ad.get().getOwner());
            return owner.getId();
        }
        return null;
    }
}
