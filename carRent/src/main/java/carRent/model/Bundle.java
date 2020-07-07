package carRent.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bundle")
public class Bundle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bundle_id_seq_gen")
    @SequenceGenerator(name="bundle_id_seq_gen", sequenceName = "bundle_id_seq", allocationSize = 1)
    Long id;

    @Column
    private Long loaner;

    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private List<Booking> bookings;

    public Bundle() {
        bookings = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoaner() {
        return loaner;
    }

    public void setLoaner(Long loaner) {
        this.loaner = loaner;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
