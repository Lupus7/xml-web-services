package carRent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_id_seq_gen")
    @SequenceGenerator(name="booking_id_seq_gen", sequenceName = "booking_id_seq", allocationSize = 1)
    Long id;

    @Column(unique = false, nullable = false)
    private LocalDateTime startDate;

    @Column(unique = false, nullable = false)
    private LocalDateTime endDate;

    @Column(name = "state", nullable = false)
    private RequestState state;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "ad")
    private Long ad;

    @Column(name = "loaner", nullable = false)
    private Long loaner;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "bundle_id", referencedColumnName = "id")
    private Bundle bundle;

    public Booking() {

    }

    public Booking(LocalDateTime startDate, LocalDateTime endDate, RequestState requestState, String place, LocalDateTime created, Long ad, Long user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = requestState;
        this.place = place;
        this.created = created;
        this.ad = ad;
        this.loaner = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState requestState) {
        this.state = requestState;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }


    public Long getLoaner() {
        return loaner;
    }

    public void setLoaner(Long loaner) {
        this.loaner = loaner;
    }

    public Long getAd() {
        return ad;
    }

    public void setAd(Long ad) {
        this.ad = ad;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
