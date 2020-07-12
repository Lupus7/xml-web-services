package agentbackend.agentback.controller.dto;

public class PriceDTO {
    protected Double price;
    protected Double priceKm;
    protected Double priceCdw;
    protected Long carId;
    protected Double percentage;
    protected Integer minDays;
    private Long pricelistId;

    public PriceDTO(Double price, Double priceKm, Double priceCdw, Long carId, Double percentage, Integer minDays) {
        this.price = price;
        this.priceKm = priceKm;
        this.priceCdw = priceCdw;
        this.carId = carId;
        this.percentage = percentage;
        this.minDays = minDays;
    }

    public PriceDTO() {
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

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Integer getMinDays() {
        return minDays;
    }

    public void setMinDays(Integer minDays) {
        this.minDays = minDays;
    }

    public Long getPricelistId() {
        return pricelistId;
    }

    public void setPricelistId(Long pricelistId) {
        this.pricelistId = pricelistId;
    }
}
