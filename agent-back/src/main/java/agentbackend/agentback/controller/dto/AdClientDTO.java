package agentbackend.agentback.controller.dto;


import agentbackend.agentback.model.Ad;
import agentbackend.agentback.model.Car;
import agentbackend.agentback.model.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdClientDTO {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String place;
    private Long carId;
    private Long adId;
    private double totalMileage;
    private double allowedMileage;
    private int childrenSeats;
    private String description;
    private boolean colDamProtection;
    private String brand;
    private String model;
    private String carClass;
    private String fuel;
    private String transmission;
    private List<String> images;
    private boolean active;
    private String advertiser;
    private Long pricelist;

    public AdClientDTO() {
    }

    public AdClientDTO(Ad ad, Car car, List<Image> images, String advertiser) {
        this(ad, car, images);
        this.advertiser = advertiser;
    }

    public AdClientDTO(Ad ad, Car car, List<Image> images) {
        this(ad, car);
        this.images = images.stream().map(Image::getEncoded64Image).collect(Collectors.toList());
        this.pricelist = ad.getPricelist();
    }

    public AdClientDTO(Ad ad, Car car) {
        this.startDate = ad.getStartDate();
        this.endDate = ad.getEndDate();
        this.place = ad.getPlace();
        this.carId = car.getId();
        this.adId = ad.getId();
        this.allowedMileage = car.getAllowedMileage();
        this.totalMileage = car.getTotalMileage();
        this.childrenSeats = car.getChildrenSeats();
        this.description = car.getDescription();
        this.colDamProtection = car.isColDamProtection();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.carClass = car.getCarClass();
        this.fuel = car.getFuel();
        this.transmission = car.getTransmission();
        this.images = new ArrayList<>();
        this.active = ad.isActive();
        /*
           this.images = car.getImages();
         */
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

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isColDamProtection() {
        return colDamProtection;
    }

    public void setColDamProtection(boolean colDamProtection) {
        this.colDamProtection = colDamProtection;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public Long getPricelist() {
        return pricelist;
    }

    public void setPricelist(Long pricelist) {
        this.pricelist = pricelist;
    }
}
