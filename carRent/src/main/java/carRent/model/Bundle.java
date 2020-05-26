package carRent.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bundle")
public class Bundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany()
    private List<Booking> bookings;

    public Bundle() {
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
}
