package agentbackend.agentback.service;


import agentbackend.agentback.controller.dto.DiscountDTO;
import agentbackend.agentback.controller.dto.Price2DTO;
import agentbackend.agentback.controller.dto.PriceDTO;
import agentbackend.agentback.model.*;
import agentbackend.agentback.repository.*;
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

    @Autowired
    private AdRepository adRepository;

    @Autowired
    CarRepository carRepository;

    public Boolean createPrice(PriceDTO priceDTO, String email) {

        if (priceDTO.getCarId() == null || priceDTO.getPrice() == null || priceDTO.getPriceKm() == null || priceDTO.getPriceCdw() == null || priceDTO.getPricelistId() == null)
            return false;

        Optional<PriceList> priceList = priceListRepository.findById(priceDTO.getPricelistId());
        if (!priceList.isPresent())
            return false;
        if (!priceList.get().getOwner().equals(email))
            return false;

        Optional<Car> car = carRepository.findById(priceDTO.getCarId());

        Price price = new Price();
        if(car.isPresent())
            price.setCar(car.get());
        price.setPrice(priceDTO.getPrice());
        price.setPriceCdw(priceDTO.getPriceCdw());
        price.setPriceKm(priceDTO.getPriceKm());
        price.setDiscounts(new ArrayList<>());
        priceRepository.save(price);

        priceList.get().getPrices().add(price);
        priceListRepository.save(priceList.get());

        return true;
    }

    public boolean editPrice(Long id, PriceDTO priceDTO, String name) {
        Optional<Price> price = priceRepository.findById(id);
        if (!price.isPresent())
            return false;

        if (priceDTO.getPrice() != null)
            price.get().setPrice(priceDTO.getPrice());
        if (priceDTO.getPriceCdw() != null)
            price.get().setPriceCdw(priceDTO.getPriceCdw());
        if (priceDTO.getPriceKm() != null)
            price.get().setPriceKm(priceDTO.getPriceKm());

        priceRepository.save(price.get());

        return true;

    }

    public boolean deletePrice(Long id, Long dto, String name) {

        if (dto == null)
            return false;

        Optional<PriceList> priceList = priceListRepository.findById(dto);
        if (!priceList.isPresent())
            return false;

        Optional<Price> price = priceRepository.findById(id);
        if (!price.isPresent())
            return false;

        priceList.get().getPrices().remove(price.get());
        priceListRepository.save(priceList.get());

        priceRepository.delete(price.get());

        return true;
    }


    //// GETTERI

    public ArrayList<Price> getAllPrices() {
        ArrayList<Price> prices = new ArrayList<>();
        prices = (ArrayList<Price>) priceRepository.findAll();
        return prices;
    }

    public Price getPricebByCarId(Long carId) {
        Optional<Price> price = priceRepository.findOneByCarId(carId);
        if (!price.isPresent())
            return null;
        return price.get();
    }

    public Double getPriceByCarId(Long carId) {
        Optional<Price> price = priceRepository.findOneByCarId(carId);
        if (!price.isPresent()) {
            return 0.0;
        }
        return price.get().getPrice();
    }

    public ArrayList<PriceList> getPriceLists() {
        return (ArrayList<PriceList>) priceListRepository.findAll();
    }

    public Price getCarPriceFromPriceList(Long carId) {
        ArrayList<PriceList> priceLists = (ArrayList<PriceList>) priceListRepository.findAll();
        for (PriceList pl : priceLists) {
            for (Price price : pl.getPrices()) {
                if (price.getCar().getId() == carId) {
                    return price;
                }
            }
        }
        return null;
    }

    public Double getPriceFromPriceListAndCars(Long priceListId, Long carId) {
        Optional<PriceList> priceList = priceListRepository.findById(priceListId);
        if (priceList == null || !priceList.isPresent())
            return 0.0;
        for (Price p : priceList.get().getPrices()) {
            if (p.getCar().getId() == carId)
                return p.getPrice();
        }
        return 0.0;
    }

    public Price2DTO getPrice(Long id, String name) {

        Optional<Price> priceOptional = priceRepository.findById(id);
        if (!priceOptional.isPresent())
            return new Price2DTO();

        Price price = priceOptional.get();

        Price2DTO price2DTO = new Price2DTO();
        price2DTO.setCarId(price.getCar().getId());
        price2DTO.setId(price.getId());
        price2DTO.setPrice(price.getPrice());
        price2DTO.setPriceCdw(price.getPriceCdw());
        price2DTO.setPriceKm(price.getPriceKm());
        for (Discount discount : price.getDiscounts()) {
            DiscountDTO discountDTO = new DiscountDTO();
            discountDTO.setId(discount.getId());
            discountDTO.setMinDays(discount.getMinDays());
            discountDTO.setPercentage(discount.getPercentage());
            price2DTO.getDiscounts().add(discountDTO);
        }

        return price2DTO;
    }

    public Price2DTO getPricesDisounts(Long id, String name) {

        Optional<Ad> ad = adRepository.findById(id);
        if (!ad.isPresent() || ad.get().getPricelist() == null)
            return new Price2DTO();

        Optional<PriceList> priceList = priceListRepository.findById(ad.get().getPricelist());
        Price price = null;
        for (Price p : priceList.get().getPrices()) {
            if (p.getCar().getId() == ad.get().getCar().getId()) {
                price = p;
                break;
                
            }
        }

        Price2DTO price2DTO = new Price2DTO();
        price2DTO.setCarId(price.getCar().getId());
        price2DTO.setId(price.getId());
        price2DTO.setPrice(price.getPrice());
        price2DTO.setPriceCdw(price.getPriceCdw());
        price2DTO.setPriceKm(price.getPriceKm());
        for (Discount discount : price.getDiscounts()) {
            DiscountDTO discountDTO = new DiscountDTO();
            discountDTO.setId(discount.getId());
            discountDTO.setMinDays(discount.getMinDays());
            discountDTO.setPercentage(discount.getPercentage());
            price2DTO.getDiscounts().add(discountDTO);
        }

        return price2DTO;

    }
}
