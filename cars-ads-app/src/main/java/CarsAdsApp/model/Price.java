package CarsAdsApp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_id_seq_gen")
    @SequenceGenerator(name="price_id_seq_gen", sequenceName = "price_id_seq", allocationSize = 1)
    protected long id;

    protected Double price;
    protected Double priceKm;
    protected Double priceCdw;
    protected Long carId;
    @ManyToMany
    @JoinTable(
            name = "price_discount",
            joinColumns =  @JoinColumn(name = "price_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id")
    )
    protected List<Discount> discounts;

    public Price() {
    }

    public Price(long id, Double price, Double priceKm, Double priceCdw, Long carId, List<Discount> discounts) {
        this.id = id;
        this.price = price;
        this.priceKm = priceKm;
        this.priceCdw = priceCdw;
        this.carId = carId;
        this.discounts = discounts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }
}
