package team10.user.models.dto;

import javax.validation.constraints.NotBlank;

public class NewAgentDTO {
    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    String address;

    @NotBlank
    String businessNumber;

    public NewAgentDTO() {
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
