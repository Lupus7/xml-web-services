package team10.admin.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PrivilegeDTO {
    @NotBlank
    String name;

    @NotNull
    boolean active;

    boolean def;

    public PrivilegeDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }
}
