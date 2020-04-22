package carRent.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_mileage", unique = false, nullable = false)
    private double totalMileage;

    @Column(name = "allowed_mileage", unique = false, nullable = false)
    private double allowedMileage;

    @Column(name = "children_seats", unique = false, nullable = false)
    private int childrenSeats;

    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @Column(name = "protection", unique = false, nullable = false)
    private boolean colDamProtection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private User owner;

    @OneToOne(cascade = CascadeType.ALL)
    private Brand brand;

    @OneToOne(cascade = CascadeType.ALL)
    private Model model;

    @OneToOne(cascade = CascadeType.ALL)
    private Fuel fuel;

    @OneToOne(cascade = CascadeType.ALL)
    private Transmission transmission;

    @OneToOne(cascade = CascadeType.ALL)
    private CarClass carClass;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "cars")
    private List<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ad> ads;

    public Car() {
    }

    public Car(double totalMileage, double allowedMileage, int childrenSeats, String description, boolean colDamProtection, User owner, Brand brand, Model model, Fuel fuel, Transmission transmission, CarClass carClass, List<Image> images) {
        this.totalMileage = totalMileage;
        this.allowedMileage = allowedMileage;
        this.childrenSeats = childrenSeats;
        this.description = description;
        this.colDamProtection = colDamProtection;
        this.owner = owner;
        this.brand = brand;
        this.model = model;
        this.fuel = fuel;
        this.transmission = transmission;
        this.carClass = carClass;
        this.bookings = new ArrayList<Booking>();
        this.images = images;
        this.ads = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Ad> getAdds() {
        return ads;
    }

    public void setAdds(List<Ad> ads) {
        this.ads = ads;
    }
}
