package CarsAdsApp.model.dto;

import CarsAdsApp.model.Discount;
import CarsAdsApp.model.PriceList;

public class PriceListDTO {
    private Long carId;
    private Double price;
    private Double priceKm;
    private Double discountPercentage;
    private Integer minDays;
    public PriceListDTO(){

    }

    public PriceListDTO(Long carId, Double price, Double priceKm, Double discountPercentage, Integer minDays) {
        this.carId = carId;
        this.price = price;
        this.priceKm = priceKm;
        this.discountPercentage = discountPercentage;
        this.minDays = minDays;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Integer getMinDays() {
        return minDays;
    }

    public void setMinDays(Integer minDays) {
        this.minDays = minDays;
    }
}
