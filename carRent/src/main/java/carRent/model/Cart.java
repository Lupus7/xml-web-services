package carRent.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user", nullable = false)
    private Long user;

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
        this.user = user;
        this.ads = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public List<Long> getAds() {
        return ads;
    }

    public void setAds(List<Long> ads) {
        this.ads = ads;
    }
}
