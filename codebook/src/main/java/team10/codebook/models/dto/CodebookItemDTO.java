package team10.codebook.models.dto;

import javax.validation.constraints.NotBlank;

public class CodebookItemDTO {
    @NotBlank
    String name;

    Long id;

    public CodebookItemDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
