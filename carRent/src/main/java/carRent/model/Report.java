package carRent.model;

import javax.persistence.*;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "extra_info", unique = false, nullable = false)
    private String extraInfo;

    @Column(name = "allowed_mileage", unique = false, nullable = false)
    private double allowedMileage;

    @OneToOne(cascade = CascadeType.ALL)
    private Booking booking;

    public Report() {
    }

    public Report(String extraInfo, double allowedMileage, Booking booking) {
        this.extraInfo = extraInfo;
        this.allowedMileage = allowedMileage;
        this.booking = booking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public double getAllowedMileage() {
        return allowedMileage;
    }

    public void setAllowedMileage(double allowedMileage) {
        this.allowedMileage = allowedMileage;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}

