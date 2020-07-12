package agentbackend.agentback.service;


import agentbackend.agentback.controller.dto.DiscountDTO;
import agentbackend.agentback.model.Discount;
import agentbackend.agentback.model.Price;
import agentbackend.agentback.repository.DiscountRepository;
import agentbackend.agentback.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscountService {

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    PriceRepository priceRepository;

    public boolean createDiscount(DiscountDTO discountDTO, String name) {

        if (discountDTO.getMinDays() == null || discountDTO.getPercentage() == null || discountDTO.getPriceId() == null)
            return false;

        Optional<Price> price = priceRepository.findById(discountDTO.getPriceId());
        if (!price.isPresent())
            return false;
        Discount discount = new Discount();
        discount.setMinDays(discountDTO.getMinDays());
        discount.setPercentage(discountDTO.getPercentage());
        discountRepository.save(discount);

        price.get().getDiscounts().add(discount);
        priceRepository.save(price.get());

        return true;

    }

    public boolean deleteDiscount(Long id, Long dto, String name) {

        if (dto == null)
            return false;

        Optional<Price> price = priceRepository.findById(dto);
        if (!price.isPresent())
            return false;

        Optional<Discount> discount = discountRepository.findById(id);
        if (!discount.isPresent())
            return false;
        price.get().getDiscounts().remove(discount.get());
        priceRepository.save(price.get());

        discountRepository.delete(discount.get());
        return true;
    }
}
