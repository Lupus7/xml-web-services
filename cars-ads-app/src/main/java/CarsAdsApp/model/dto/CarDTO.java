package CarsAdsApp.model.dto;

import CarsAdsApp.model.Car;

import java.util.ArrayList;
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

    public CarDTO(Car car) {
        this.carId = car.getId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.carClass = car.getCarClass();
        this.totalMileage = car.getTotalMileage();
        this.allowedMileage = car.getAllowedMileage();
        this.childrenSeats = car.getChildrenSeats();
        this.description = car.getDescription();
        this.colDamProtection = car.isColDamProtection();
        this.fuel = car.getFuel();
        this.transmission = car.getTransmission();
        this.images = new ArrayList<>();
    }

    public CarDTO() {
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
}
