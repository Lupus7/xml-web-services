package agentbackend.agentback.controller.dto;

public class AdDTO {

    private String startDate;

    private String endDate;

    private String place;

    private Long carId;

    public AdDTO() {
    }

    public AdDTO(String startDate, String endDate, String place, Long car) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.carId = car;
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

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long car) {
        this.carId = car;
    }
}
