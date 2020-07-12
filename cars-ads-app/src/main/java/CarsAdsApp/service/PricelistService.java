package CarsAdsApp.service;

import CarsAdsApp.model.Discount;
import CarsAdsApp.model.Price;
import CarsAdsApp.model.PriceList;
import CarsAdsApp.model.dto.*;
import CarsAdsApp.proxy.UserProxy;
import CarsAdsApp.repository.DiscountRepository;
import CarsAdsApp.repository.PriceListRepository;
import CarsAdsApp.repository.PriceRepository;
import org.apache.http.impl.auth.DigestScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PricelistService {

    @Autowired
    PriceListRepository priceListRepository;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    UserProxy userProxy;

    public ResponseEntity<PricelistDTO> createPricelist(PricelistDTO dto, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, email + ";MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return ResponseEntity.badRequest().body(null);

        if (dto.getName() == null || dto.getName().equals(""))
            return ResponseEntity.badRequest().body(null);
        PriceList priceList = new PriceList(dto.getName(), email);
        priceListRepository.save(priceList);

        PricelistDTO pricelistDTO = new PricelistDTO();
        priceList.setOwner(priceList.getOwner());
        priceList.setName(priceList.getName());
        priceList.setId(priceList.getId());

        return ResponseEntity.ok(pricelistDTO);
    }

    public ResponseEntity<String> deletePricelist(Long id, String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, email + ";MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return ResponseEntity.badRequest().body(null);

        if (id == null)
            return ResponseEntity.badRequest().body("Pricelist doesnt exist!");

        Optional<PriceList> priceListOptional = priceListRepository.findById(id);
        if (!priceListOptional.isPresent())
            return ResponseEntity.badRequest().body("Pricelist doesnt exist!");

        if (!priceListOptional.get().getOwner().equals(email))
            return ResponseEntity.badRequest().body("Error!");

        List<Price> prices = new ArrayList<>();
        List<Discount> discounts = new ArrayList<>();
        for (Price price : priceListOptional.get().getPrices()) {
            for (Discount discount : price.getDiscounts()) {
                discounts.add(discount);
            }
            prices.add(price);
        }

        for (Price p : priceListOptional.get().getPrices()) {
            p.setDiscounts(new ArrayList<>());
            priceRepository.save(p);
        }

        priceListOptional.get().setPrices(new ArrayList<>());
        priceListRepository.save(priceListOptional.get());

        for (Discount d : discounts)
            discountRepository.delete(d);
        for (Price p1 : prices)
            priceRepository.delete(p1);

        priceListRepository.delete(priceListOptional.get());

        return ResponseEntity.ok("Pricelist deleted!");

    }

    public List<PricelistDTO> getPriceLists(String email) {
        List<PricelistDTO> list = new ArrayList<>();
        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email, email + ";MASTER");
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return list;
        List<PriceList> pricelists = priceListRepository.findAllByOwner(email);
        for (PriceList pl : pricelists) {
            PricelistDTO pricelistDTO = new PricelistDTO();
            pricelistDTO.setId(pl.getId());
            pricelistDTO.setName(pl.getName());
            pricelistDTO.setOwner(pl.getOwner());
            for (Price price : pl.getPrices()) {
                Price2DTO price2DTO = new Price2DTO();
                price2DTO.setCarId(price.getCarId());
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
                pricelistDTO.getPrices().add(price2DTO);

            }

            list.add(pricelistDTO);
        }

        return list;

    }

    public PricelistDTO getPriceList(Long id, String name) {

        Optional<PriceList> pricelists = priceListRepository.findById(id);

        if (!pricelists.isPresent())
            return new PricelistDTO();

        PriceList pl = pricelists.get();

        PricelistDTO pricelistDTO = new PricelistDTO();
        pricelistDTO.setId(pl.getId());
        pricelistDTO.setName(pl.getName());
        pricelistDTO.setOwner(pl.getOwner());
        for (Price price : pl.getPrices()) {
            Price2DTO price2DTO = new Price2DTO();
            price2DTO.setCarId(price.getCarId());
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
            pricelistDTO.getPrices().add(price2DTO);

        }

        return pricelistDTO;
    }
}
