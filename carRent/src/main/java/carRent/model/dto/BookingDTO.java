package carRent.model.dto;

import carRent.model.Booking;
import carRent.model.RequestState;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDTO {

    Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private RequestState state;

    private String place;

    private LocalDateTime created;

    private List<Long> cars;

    public BookingDTO() {

    }

    public BookingDTO(Booking booking) {
        this.id = booking.getId();
        this.startDate = booking.getStartDate();
        this.endDate = booking.getEndDate();
        this.state = booking.getState();
        this.place = booking.getPlace();
        this.created = booking.getCreated();
        for (Long car : booking.getCars())
            this.cars.add(car);

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

    public void setState(RequestState state) {
        this.state = state;
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

    public List<Long> getCars() {
        return cars;
    }

    public void setCars(List<Long> cars) {
        this.cars = cars;
    }
}
