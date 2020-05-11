package team10.admin.models.dto;

import javax.validation.constraints.NotBlank;

public class NewCompanyDTO {
    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotBlank
    String name;

    @NotBlank
    String address;

    @NotBlank
    String businessNumber;

    public NewCompanyDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
