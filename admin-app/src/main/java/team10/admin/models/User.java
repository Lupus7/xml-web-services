package team10.admin.models;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "first_name", unique = false, nullable = false)
    private String firstName;

    @Column(name = "last_name", unique = false, nullable = false)
    private String lastName;

    @Column(name = "company_name", unique = false, nullable = false)
    private String companyName;

	@Column(name = "business_number", unique = true, nullable = true)
    private String businessNumber;

    @Column(name = "address", unique = false, nullable = false)
    private String address;
	
    @Column(name = "password", unique = false, nullable = false)
    private String password;

    @Column(name = "blocked", unique = false, nullable = false)
    private Boolean blocked;

    @Column(name = "approved", unique = false, nullable = false)
    private Boolean approved;

    @Column(name = "authorities", unique = false, nullable = false)
    private String authorities;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Car> cars;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public List<String> getRoles() {
        return Arrays.asList(authorities.split(";"))
                .stream()
                .filter(authority -> authority.startsWith("ROLE"))
                .collect(Collectors.toList());
    }

    public List<String> getPrivileges() {
        return Arrays.asList(authorities.split(";"))
                .stream()
                .filter(authority -> !authority.startsWith("ROLE"))
                .collect(Collectors.toList());
    }
}
