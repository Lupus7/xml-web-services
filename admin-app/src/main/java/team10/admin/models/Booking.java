package team10.admin.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "booking_car", joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
    private List<Car> cars;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="loaner",referencedColumnName = "id")
    private User loaner;

    public Booking() {

    }

    public Booking(LocalDateTime startDate, LocalDateTime endDate, RequestState requestState, String place, LocalDateTime created, Car car, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = requestState;
        this.place = place;
        this.created = created;
        this.cars.add(car);
        this.loaner = user;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public User getUser() {
        return loaner;
    }

    public void setUser(User user) {
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

    public User getLoaner() {
        return loaner;
    }

    public void setLoaner(User loaner) {
        this.loaner = loaner;
    }
}
