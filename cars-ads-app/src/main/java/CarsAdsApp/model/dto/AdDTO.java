package CarsAdsApp.model.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class AdDTO {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String place;

    private Long carId;

    public AdDTO() {
    }

    public AdDTO(LocalDateTime startDate, LocalDateTime endDate, String place, Long car) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.carId = car;
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
}
