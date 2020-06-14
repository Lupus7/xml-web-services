package carRent.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_seq_gen")
    @SequenceGenerator(name="cart_id_seq_gen", sequenceName = "cart_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ElementCollection
    @CollectionTable(
            name = "cart_ads",
            joinColumns=@JoinColumn(name = "cart_id", referencedColumnName = "id")
    )
    @Column(name = "ad_id")
    private List<Long> ads;

    public Cart() {
    }

    public Cart(Long user) {
        this.userId = user;
        this.ads = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getAds() {
        return ads;
    }

    public void setAds(List<Long> ads) {
        this.ads = ads;
    }
}
