package CarsAdsApp.model.dto;

import java.util.ArrayList;
import java.util.List;

public class Price2DTO {

    private Long id;
    private Double price;
    private Double priceKm;
    private Double priceCdw;
    private Long carId;
    private List<DiscountDTO> discounts = new ArrayList<>();

    public Price2DTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceKm() {
        return priceKm;
    }

    public void setPriceKm(Double priceKm) {
        this.priceKm = priceKm;
    }

    public Double getPriceCdw() {
        return priceCdw;
    }

    public void setPriceCdw(Double priceCdw) {
        this.priceCdw = priceCdw;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public List<DiscountDTO> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountDTO> discounts) {
        this.discounts = discounts;
    }
}
