package team10.admin.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    private LocalDateTime startDate;

    @Column(unique = false, nullable = false)
    private LocalDateTime endDate;

    @Column(name = "place", unique = false, nullable = false)
    private String place;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="car_id",referencedColumnName = "id")
    private Car car;

    public Ad() {

    }

    public Ad(LocalDateTime startDate, LocalDateTime endDate, String place, Car car) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.car = car;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }


}

