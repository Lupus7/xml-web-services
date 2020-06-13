package agentbackend.agentback.controller.dto;

public class ClientDTO {
    String email;

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
