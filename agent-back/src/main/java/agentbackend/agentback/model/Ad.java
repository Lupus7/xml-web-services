package agentbackend.agentback.model;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_id_seq_gen")
    @SequenceGenerator(name="ad_id_seq_gen", sequenceName = "ad_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = false, nullable = false)
    private LocalDateTime startDate;

    @Column(unique = false, nullable = false)
    private LocalDateTime endDate;

    @Column(name = "place", unique = false, nullable = false)
    private String place;

    @Column(name = "car_id", unique = false, nullable = false)
    private Long carId;

    @Column(name = "owner_id", unique = false, nullable = false)
    private Long ownerId;

    @Column(name = "active", unique = false, nullable = false)
    private boolean active;

    @Column()
    private Long serviceId;

    public Ad() {

    }

    public Ad(LocalDateTime startDate, LocalDateTime endDate, String place, Long car, Long owner) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.carId = car;
        this.ownerId = owner;
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

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long car) {
        this.carId = car;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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
}

