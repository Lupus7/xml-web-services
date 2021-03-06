package agentbackend.agentback.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @Column(name = "service_id", unique = true, nullable = true)
    private Long serviceId;

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

//    @Column(name = "authorities", unique = false, nullable = false, columnDefinition="text")
//    private String authorities;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "user_bookings", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "booking_id")
    private List<Long> bookings;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "user_cars", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "car_id")
    private List<Long> cars;

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

    public List<Long> getBookings() {
        return bookings;
    }

    public void setBookings(List<Long> bookings) {
        this.bookings = bookings;
    }

    public List<Long> getCars() {
        return cars;
    }

    public void setCars(List<Long> cars) {
        this.cars = cars;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    //    public String getAuthorities() {
//        return authorities;
//    }
//
//    public void setAuthorities(String authorities) {
//        this.authorities = authorities;
//    }

//    public List<String> getRoles() {
//        return Arrays.asList(authorities.split(";"))
//                .stream()
//                .filter(authority -> authority.startsWith("ROLE"))
//                .collect(Collectors.toList());
//    }
//
//    public List<String> getPrivileges() {
//        return Arrays.asList(authorities.split(";"))
//                .stream()
//                .filter(authority -> !authority.startsWith("ROLE"))
//                .collect(Collectors.toList());
//    }
}
