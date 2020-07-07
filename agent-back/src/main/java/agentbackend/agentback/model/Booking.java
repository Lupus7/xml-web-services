package agentbackend.agentback.model;

import com.car_rent.agent_api.wsdl.BookingDetails;

import javax.persistence.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_id_seq_gen")
    @SequenceGenerator(name = "booking_id_seq_gen", sequenceName = "booking_id_seq", allocationSize = 1)
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

    @ManyToOne
    @JoinColumn(name = "ad", nullable = false)
    private Ad ad;

    @Column(name = "loaner")
    private String loaner;

    @Column()
    private Long serviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id", referencedColumnName = "id")
    private Bundle bundle;

    public Booking() {

    }

    public Booking(LocalDateTime startDate, LocalDateTime endDate, RequestState requestState, String place, LocalDateTime created, Ad ad, String user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = requestState;
        this.place = place;
        this.created = created;
        this.ad = ad;
        this.loaner = user;
    }

    public Booking(BookingDetails bookDetail, Ad ad) {
        this.serviceId = bookDetail.getId();
        this.bundle = null;
        this.ad = ad;
        this.place = bookDetail.getPlace();
        if (bookDetail.getState() == 0)
            this.state = RequestState.PENDING;
        else if (bookDetail.getState() == 1)
            this.state = RequestState.PAID;
        else if (bookDetail.getState() == 2)
            this.state = RequestState.CANCELED;
        else if (bookDetail.getState() == 3)
            this.state = RequestState.ENDED;
        this.created = convert(bookDetail.getCreated());
        this.startDate = convert(bookDetail.getStartDate());
        this.endDate = convert(bookDetail.getEndDate());
        this.loaner = bookDetail.getAdvertiser();

    }

    public Booking(BookingDetails bookDetail, Ad ad, Bundle bundle) {
        this.serviceId = bookDetail.getId();
        this.bundle = bundle;
        this.ad = ad;
        this.place = bookDetail.getPlace();
        if (bookDetail.getState() == 0)
            this.state = RequestState.PENDING;
        else if (bookDetail.getState() == 1)
            this.state = RequestState.PAID;
        else if (bookDetail.getState() == 2)
            this.state = RequestState.CANCELED;
        else if (bookDetail.getState() == 3)
            this.state = RequestState.ENDED;
        this.created = convert(bookDetail.getCreated());
        this.startDate = convert(bookDetail.getStartDate());
        this.endDate = convert(bookDetail.getEndDate());
        this.loaner = bundle.getLoaner();

    }

    public LocalDateTime convert(XMLGregorianCalendar date) {

        return LocalDateTime.of(
                date.getYear(),
                date.getMonth(),
                date.getDay(), 0, 0, 0);

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

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public String getLoaner() {
        return loaner;
    }

    public void setLoaner(String loaner) {
        this.loaner = loaner;
    }
}
