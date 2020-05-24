package carRent.model.dto;

import java.time.LocalDateTime;

public class AdDTO {

    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String place;

    private Long carId;

    public AdDTO() {
    }

    public AdDTO(Long id, LocalDateTime startDate, LocalDateTime endDate, String place, Long carId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.carId = carId;
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

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
