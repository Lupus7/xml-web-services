package carRent.model;

import javax.persistence.*;

@Entity
@Table(name = "rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate", unique = false, nullable = false)
    private int rate;

    @Column(name = "comment", unique = false, nullable = false)
    private String comment;

    @Column(name = "approved", unique = false, nullable = false)
    private boolean approved;

    @OneToOne(cascade = CascadeType.ALL)
    private Booking booking;

    public Rate() {
    }

    public Rate(int rate, String comment, boolean approved, Booking booking) {
        this.rate = rate;
        this.comment = comment;
        this.approved = approved;
        this.booking = booking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
