package carRent.model;

import javax.persistence.Column;

public class Company extends User{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(unique = true, nullable = false)
    private String businessNumber;

    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }
}
