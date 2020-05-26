package CarsAdsApp.service;

import CarsAdsApp.model.Ad;
import CarsAdsApp.model.User;
import CarsAdsApp.model.dto.AdDTO;
import CarsAdsApp.repository.AdRepository;
import CarsAdsApp.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AdService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AdRepository adRepo;

    public int createAd(AdDTO adDTO, String email) {
        // provera da li user sa name postoji, provera da li je ad userov
        User user = userRepo.findByEmail(email);
        if (user == null)
            return 400;

        // provera za 3 ad-a
        ArrayList<Ad> ads = adRepo.findAllByOwnerIdAndActive(user.getId(), true);
        if (ads.size() == 3)
            return 402;


        Ad ad = new Ad(adDTO, user.getId());
        adRepo.save(ad);

        return 200;
    }

    public boolean checkAds(JSONObject object, String email) throws JSONException {

        // provera da li user sa name postoji, provera da li je ad userov
        User user = userRepo.findByEmail(email);
        if (user == null)
            return false;

        if (object == null || object.get("array") == null)
            return false;

        JSONArray array = (JSONArray) object.get("array");

        for (int i = 0; i < array.length(); i++) {
            Long id = array.getLong(i);
            Optional<Ad> ad = adRepo.findById(id);
            if (!ad.isPresent() || ad.get().getOwnerId() != user.getId())
                return false;
        }


        return true;
    }

    public int activateAd(Long id, String name) {

        // provera da li user sa name postoji, provera da li je ad userov
        User user = userRepo.findByEmail(name);
        if (user == null)
            return 400;

        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent() || ad.get().getOwnerId() != user.getId() || ad.get().isActive())
            return 400;

        ArrayList<Ad> ads = adRepo.findAllByOwnerIdAndActive(user.getId(), true);
        if (ads.size() == 3)
            return 402;


        ad.get().setActive(true);
        adRepo.save(ad.get());

        return 200;
    }

    public boolean deactivateAd(Long id, String name) {

        // provera da li user sa name postoji, provera da li je ad userov
        User user = userRepo.findByEmail(name);
        if (user == null)
            return false;

        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent() || ad.get().getOwnerId() != user.getId() || !ad.get().isActive())
            return false;

        ad.get().setActive(false);
        adRepo.save(ad.get());

        return true;
    }

    public boolean editAd(Long id, AdDTO adDTO, String name) {
        // provera da li user sa name postoji, provera da li je ad userov
        User user = userRepo.findByEmail(name);
        if (user == null)
            return false;

        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent() || ad.get().getOwnerId() != user.getId() || !ad.get().isActive())
            return false;

        if (adDTO.getStartDate() != null)
            ad.get().setStartDate(adDTO.getStartDate());

        if (adDTO.getEndDate() != null)
            ad.get().setEndDate(adDTO.getEndDate());

        if (adDTO.getPlace() != null)
            ad.get().setPlace(adDTO.getPlace());

        adRepo.save(ad.get());

        return true;
    }

}
