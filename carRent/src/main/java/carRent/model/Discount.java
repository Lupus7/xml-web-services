package carRent.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "percent", unique = false, nullable = false)
    private int percent;

    @Column(name = "min_days", unique = false, nullable = false)
    private int minDays;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Price> prices;

    public Discount() {
    }

    public Discount(int percent, int minDays, List<Price> prices) {
        this.percent = percent;
        this.minDays = minDays;
        this.prices = prices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getMinDays() {
        return minDays;
    }

    public void setMinDays(int minDays) {
        this.minDays = minDays;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }
}
