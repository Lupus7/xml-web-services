package carRent.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", unique = false, nullable = false)
    private LocalDateTime date;

    @Column(name = "price", unique = false, nullable = false)
    private double price;

    @Column(name = "priceKm", unique = false, nullable = false)
    private double priceKm;

    @Column(name = "priceCDW", unique = false, nullable = false)
    private double priceCDW;

    @OneToOne(cascade = CascadeType.ALL)
    private Ad ad;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="discount_id",referencedColumnName = "id")
    private Discount discount;

    public Price() {
    }

    public Price(LocalDateTime date, double price, double priceKm, double priceCDW, Ad ad, Discount discount) {
        this.date = date;
        this.price = price;
        this.priceKm = priceKm;
        this.priceCDW = priceCDW;
        this.ad = ad;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceKm() {
        return priceKm;
    }

    public void setPriceKm(double priceKm) {
        this.priceKm = priceKm;
    }

    public double getPriceCDW() {
        return priceCDW;
    }

    public void setPriceCDW(double priceCDW) {
        this.priceCDW = priceCDW;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
