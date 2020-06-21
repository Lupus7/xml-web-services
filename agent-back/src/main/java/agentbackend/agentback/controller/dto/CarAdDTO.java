package agentbackend.agentback.controller.dto;

public class CarAdDTO {
    private String startDate;

    private String endDate;

    private String place;

    private CarDTO car;

    private double rating;

    private Long id;

    public CarAdDTO() {
    }

    public CarAdDTO(Long id, String startDate, String endDate, String place, CarDTO car) {
        this.id = id;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
