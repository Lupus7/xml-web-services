package agentbackend.agentback.model;

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

    @Column()
    private Long serviceId;

    @Column(name = "loaner")
    private String loaner;

    @OneToMany()
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

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getLoaner() {
        return loaner;
    }

    public void setLoaner(String loaner) {
        this.loaner = loaner;
    }
}
