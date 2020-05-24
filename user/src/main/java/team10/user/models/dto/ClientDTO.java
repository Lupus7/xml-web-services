package team10.user.models.dto;

import javax.validation.constraints.NotNull;

public class ClientDTO {
    String email;

    @NotNull
    boolean blocked;

    public ClientDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
