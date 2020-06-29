package agentbackend.agentback.model;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_id_seq_gen")
    @SequenceGenerator(name = "ad_id_seq_gen", sequenceName = "ad_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(name = "place", unique = false, nullable = false)
    private String place;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "active", unique = false, nullable = false)
    private boolean active;

    @Column()
    private Long serviceId;

    public Ad() {

    }

    public Ad(LocalDateTime startDate, LocalDateTime endDate, String place, Car car, String owner) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.car = car;
        this.owner = owner;
        this.active = true;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}

