package agentbackend.agentback.controller.dto;

public class ReportDto {

    private String extraInfo;
    private double allowedMileage;
    private long booking;

    public ReportDto() {
    }

    public ReportDto(String extraInfo, double allowedMileage, long booking) {
        this.extraInfo = extraInfo;
        this.allowedMileage = allowedMileage;
        this.booking = booking;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public double getAllowedMileage() {
        return allowedMileage;
    }

    public void setAllowedMileage(double allowedMileage) {
        this.allowedMileage = allowedMileage;
    }

    public long getBooking() {
        return booking;
    }

    public void setBooking(long booking) {
        this.booking = booking;
    }
}
