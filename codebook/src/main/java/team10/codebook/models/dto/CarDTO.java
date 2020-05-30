package team10.codebook.models.dto;

import java.util.List;

public class CarDTO {

    private Long carId;
    private String brand;
    private String model;
    private String carClass;
    private double totalMileage;
    private double allowedMileage;
    private int childrenSeats;
    private String description;
    private boolean colDamProtection;
    private String fuel;
    private String transmission;
    private List<String> images;

    public CarDTO() {
    }

    public CarDTO(Long carId, String brand, String model, String carClass, double totalMileage, double allowedMileage, int childrenSeats, String description, boolean colDamProtection, String fuel, String transmission, List<String> images) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.carClass = carClass;
        this.totalMileage = totalMileage;
        this.allowedMileage = allowedMileage;
        this.childrenSeats = childrenSeats;
        this.description = description;
        this.colDamProtection = colDamProtection;
        this.fuel = fuel;
        this.transmission = transmission;
        this.images = images;
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
}
