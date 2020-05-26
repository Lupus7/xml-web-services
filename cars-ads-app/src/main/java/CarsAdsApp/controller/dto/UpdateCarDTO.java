package CarsAdsApp.controller.dto;

public class UpdateCarDTO {
    private double totalMileage;
    private double allowedMileage;
    private boolean colDamProtection;
    private String description;

    public UpdateCarDTO(double totalMileage, double allowedMileage, boolean colDamProtection, String description) {
        this.totalMileage = totalMileage;
        this.allowedMileage = allowedMileage;
        this.colDamProtection = colDamProtection;
        this.description = description;
    }

    public UpdateCarDTO() {
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

    public boolean isColDamProtection() {
        return colDamProtection;
    }

    public void setColDamProtection(boolean colDamProtection) {
        this.colDamProtection = colDamProtection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
