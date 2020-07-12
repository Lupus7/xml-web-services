package agentbackend.agentback.controller.dto;

import agentbackend.agentback.model.Rate;

import java.util.List;

public class CarRateDTO {

    private Long carId;
    private String brand;
    private String model;
    private String carClass;
    private double totalMileage;
    private double allowedMileage;
    private int childrenSeats;
    private boolean colDamProtection;
    private String fuel;
    private String transmission;
    private List<String> images;
    private String advertiser;
    private int rate;
    private String comment;
    private String recomment;
    private Long rateId;
    private boolean approved;
    private String user;

    public CarRateDTO(Rate rate, CarDTO carDTO, String owner) {
        this.carId = rate.getCarId();
        this.brand = carDTO.getBrand();
        this.model = carDTO.getModel();
        this.carClass = carDTO.getCarClass();
        this.totalMileage = carDTO.getTotalMileage();
        this.allowedMileage = carDTO.getAllowedMileage();
        this.childrenSeats = carDTO.getChildrenSeats();
        this.colDamProtection = carDTO.isColDamProtection();
        this.fuel = carDTO.getFuel();
        this.transmission = carDTO.getTransmission();
        this.images = carDTO.getImages();
        this.advertiser = owner;
        this.rate = rate.getRate();
        this.comment = rate.getComment();
        this.rateId = rate.getId();
        this.approved = rate.isApproved();
        this.user = rate.getRater();
        this.recomment = rate.getRecomment();
    }

    public CarRateDTO() {
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public double getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(double totalMileage) {
        this.totalMileage = totalMileage;
    }

    public double getAllowedMileage() {
        return allowedMileage;
    }

    public void setAllowedMileage(double allowedMileage) {
        this.allowedMileage = allowedMileage;
    }

    public int getChildrenSeats() {
        return childrenSeats;
    }

    public void setChildrenSeats(int childrenSeats) {
        this.childrenSeats = childrenSeats;
    }


    public boolean isColDamProtection() {
        return colDamProtection;
    }

    public void setColDamProtection(boolean colDamProtection) {
        this.colDamProtection = colDamProtection;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getRateId() {
        return rateId;
    }

    public void setRateId(Long rateId) {
        this.rateId = rateId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRecomment() {
        return recomment;
    }

    public void setRecomment(String recomment) {
        this.recomment = recomment;
    }
}
