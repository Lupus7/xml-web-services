package com.example.community.controller.dto;

public class ReportDto {

    private String extraInfo;
    private double allowedMileage;
    private long booking;
    private double travelledKm;

    public ReportDto() {
    }

    public ReportDto(String extraInfo, double allowedMileage, long booking, double travelledKm) {
        this.extraInfo = extraInfo;
        this.allowedMileage = allowedMileage;
        this.booking = booking;
        this.travelledKm = travelledKm;
    }

    public double getTravelledKm() {
        return travelledKm;
    }

    public void setTravelledKm(double travelledKm) {
        this.travelledKm = travelledKm;
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
