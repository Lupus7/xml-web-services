package CarsAdsApp.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class NewCarDTO {
    private double totalMileage;
    private double allowedMilleage;
    private int childrenSeats;
    private String description;
    private boolean colDamProtection;
    private String owned;
    private String brand;
    private String model;
    private String carClass;
    private String fuel;
    private String transmission;
    private List<String> images = new ArrayList<>();

    public NewCarDTO() {
    }

    public NewCarDTO(double totalMileage, double allowedMilleage, int childrenSeats, String description, boolean colDamProtection, String owned, String brand, String model, String carClass, String fuel, String transmission, List<String> images) {
        this.totalMileage = totalMileage;
        this.allowedMilleage = allowedMilleage;
        this.childrenSeats = childrenSeats;
        this.description = description;
        this.colDamProtection = colDamProtection;
        this.owned = owned;
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

    public double getAllowedMilleage() {
        return allowedMilleage;
    }

    public void setAllowedMilleage(double allowedMilleage) {
        this.allowedMilleage = allowedMilleage;
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

    public String getOwned() {
        return owned;
    }

    public void setOwned(String owned) {
        this.owned = owned;
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
