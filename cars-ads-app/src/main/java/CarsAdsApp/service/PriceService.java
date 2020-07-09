package CarsAdsApp.service;

import CarsAdsApp.model.Discount;
import CarsAdsApp.model.Price;
import CarsAdsApp.model.PriceList;
import CarsAdsApp.model.dto.PriceDTO;
import CarsAdsApp.repository.DiscountRepository;
import CarsAdsApp.repository.PriceListRepository;
import CarsAdsApp.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PriceService {
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private PriceListRepository priceListRepository;
    @Autowired
    private DiscountRepository discountRepository;

    public Boolean createPrice(PriceDTO priceDTO){
        Price price = new Price();
        price.setCarId(priceDTO.getCarId());
        price.setPrice(priceDTO.getPrice());
        price.setPriceCdw(priceDTO.getPriceCdw());
        price.setPriceKm(priceDTO.getPriceKm());
        Discount discount = new Discount(priceDTO.getPercentage(), priceDTO.getMinDays());
        discountRepository.save(discount);
        price.setDiscounts(new ArrayList<>());
        price.getDiscounts().add(discount);
        priceRepository.save(price);
        return true;
    }

    public ArrayList<Price> getAllPrices(){
        ArrayList<Price> prices = new ArrayList<>();
        prices = (ArrayList<Price>) priceRepository.findAll();
        return prices;
    }

    public Price getPricebByCarId(Long carId){
        Optional<Price> price = priceRepository.findOneByCarId(carId);
        if(!price.isPresent())
            return null;
        return price.get();
    }

    public Double getPriceByCarId(Long carId){
        Optional<Price> price = priceRepository.findOneByCarId(carId);
        if(!price.isPresent()){
           return 0.0;
        }
        return price.get().getPrice();
    }

    public ArrayList<PriceList> getPriceLists(){
        return (ArrayList<PriceList>) priceListRepository.findAll();
    }

    public Price getCarPriceFromPriceList(Long carId){
        ArrayList<PriceList> priceLists = (ArrayList<PriceList>) priceListRepository.findAll();
        for(PriceList pl: priceLists){
            for(Price price: pl.getPrices()){
                if(price.getCarId() == carId){
                    return price;
                }
            }
        }
        return null;
    }

    public Double getPriceFromPriceListAndCars(Long priceListId, Long carId){
        Optional<PriceList> priceList = priceListRepository.findById(priceListId);
        if (priceList == null || !priceList.isPresent())
            return 0.0;
        for(Price p: priceList.get().getPrices()){
            if(p.getCarId() == carId)
                return p.getPrice();
        }
        return 0.0;
    }
}
