package agentbackend.agentback.controller.dto;

public class StatisticsDTO {
    private Long carId;
    private String carName;
    private Long totalMileage;
    private Long commNum;
    private Double rating;

    public StatisticsDTO() {
    }

    public StatisticsDTO(Long carId, String carName, Long totalMileage, Long commNum, Double rating) {
        this.carId = carId;
        this.carName = carName;
        this.totalMileage = totalMileage;
        this.commNum = commNum;
        this.rating = rating;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Long getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(Long totalMileage) {
        this.totalMileage = totalMileage;
    }

    public Long getCommNum() {
        return commNum;
    }

    public void setCommNum(Long commNum) {
        this.commNum = commNum;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
