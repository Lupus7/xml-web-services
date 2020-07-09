package CarsAdsApp.model;

import javax.persistence.*;

@Entity
@Table(name="discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_id_seq_gen")
    @SequenceGenerator(name="discount_id_seq_gen", sequenceName = "discount_id_seq", allocationSize = 1)
    protected long id;

    protected Double percentage;
    protected Integer minDays;

    public Discount(long id, Double percentage, Integer minDays) {
        this.id = id;
        this.percentage = percentage;
        this.minDays = minDays;
    }

    public Discount(Double percentage, Integer minDays) {
        this.percentage = percentage;
        this.minDays = minDays;
    }

    public Discount(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


}
