package CarsAdsApp.service.soap;

import CarsAdsApp.model.Ad;
import CarsAdsApp.model.Car;
import CarsAdsApp.model.Image;
import CarsAdsApp.model.dto.AdClientDTO;
import CarsAdsApp.model.dto.AdDTO;
import CarsAdsApp.proxy.UserProxy;
import CarsAdsApp.repository.AdRepository;
import CarsAdsApp.repository.CarRepository;
import CarsAdsApp.repository.ImageRepository;
import com.car_rent.agent_api.AdDetails;
import com.car_rent.agent_api.AdFormDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class SoapAdService {

    @Autowired
    private AdRepository adRepo;

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    UserProxy userProxy;

    public Long createAdSoap(AdDTO adDTO, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return (long) -1;
        Long userId = userIdResponse.getBody();

        if (adDTO == null)
            return (long) -1;
        if (adDTO.getPlace() == null || adDTO.getStartDate() == null || adDTO.getEndDate() == null || adDTO.getCarId() == null)
            return (long) -1;
        if (adDTO.getPlace().equals("") || adDTO.getStartDate().equals("") || adDTO.getEndDate().equals(""))
            return (long) -1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Ad ad = new Ad(LocalDateTime.parse(adDTO.getStartDate(), formatter), LocalDateTime.parse(adDTO.getEndDate(), formatter), adDTO.getPlace(), adDTO.getCarId(), userId, true, adDTO.getPriceListId());
        adRepo.save(ad);

        return ad.getId();
    }

    public boolean activateAdSoap(Long id, String email) {

        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent() || ad.get().isActive())
            return false;
        ResponseEntity<String> userEmail = userProxy.getUserEmail(ad.get().getOwnerId());
        if (userEmail == null || !email.equals(userEmail.getBody()))
            return false;

        ad.get().setActive(true);
        adRepo.save(ad.get());

        return true;
    }

    public boolean deactivateAdSoap(Long id, String email) {
        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent() || !ad.get().isActive())
            return false;
        ResponseEntity<String> userEmail = userProxy.getUserEmail(ad.get().getOwnerId());
        if (userEmail == null || !email.equals(userEmail.getBody()))
            return false;

        ad.get().setActive(false);
        adRepo.save(ad.get());

        return true;
    }

    public Long editAd(long id, AdFormDetails adDTO, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return (long) -1;

        Long userId = userIdResponse.getBody();

        Optional<Ad> ad = adRepo.findById(id);
        if (!ad.isPresent() || ad.get().getOwnerId() != userId)
            return (long) -1;

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

        return ad.get().getId();
    }

    public List<AdDetails> getAds(String email) throws DatatypeConfigurationException {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return new ArrayList<>();

        Long userId = userIdResponse.getBody();
        List<AdDetails> adClientDTOS = new ArrayList<>();

        List<Ad> ads = adRepo.findAllByOwnerId(userId);
        for (Ad ad : ads) {
            Optional<Car> car = carRepo.findById(ad.getCarId());
            if (car.isPresent()) {
                List<Image> images = imageRepo.findAllByCarId(car.get().getId());
                List<String> imagesString = new ArrayList<>();
                if (images == null)
                    images = new ArrayList<>();
                else {
                    for (Image image : images)
                        imagesString.add(image.getEncoded64Image());
                }

                AdDetails adClientDTO = new AdDetails();
                adClientDTO.setActive(ad.isActive());
                adClientDTO.setAdId(ad.getId());
                adClientDTO.setAdvertiser(car.get().getOwner());
                adClientDTO.setAllowedMileage(car.get().getAllowedMileage());
                adClientDTO.setTotalMileage(car.get().getTotalMileage());
                adClientDTO.setChildrenSeats(car.get().getChildrenSeats());
                adClientDTO.setCarId(car.get().getId());
                adClientDTO.setBrand(car.get().getBrand());
                adClientDTO.setModel(car.get().getModel());
                adClientDTO.setCarClass(car.get().getCarClass());
                adClientDTO.setFuel(car.get().getFuel());
                adClientDTO.setTransmission(car.get().getTransmission());
                adClientDTO.getImages().addAll(imagesString);
                adClientDTO.setDescription(car.get().getDescription());
                adClientDTO.setColDamProtection(car.get().isColDamProtection());
                adClientDTO.setPlace(ad.getPlace());
                adClientDTO.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(ad.getStartDate().toString()));
                adClientDTO.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(ad.getEndDate().toString()));


                adClientDTOS.add(adClientDTO);
            }
        }

        return adClientDTOS;
    }


}
