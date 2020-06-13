package agentbackend.agentback.controller.dto;

import java.util.List;

public class UpdateCarDTO {
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

    public UpdateCarDTO() {
    }

    public UpdateCarDTO(double totalMileage, double allowedMileage, int childrenSeats, String description, boolean colDamProtection, String brand, String model, String carClass, String fuel, String transmission, List<String> images) {
        this.totalMileage = totalMileage;
        this.allowedMileage = allowedMileage;
        this.childrenSeats = childrenSeats;
        this.description = description;
        this.colDamProtection = colDamProtection;
        this.brand = brand;
        this.model = model;
        this.carClass = carClass;
        this.fuel = fuel;
        this.transmission = transmission;
        this.images = images;
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
}
