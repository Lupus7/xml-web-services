package team10.codebook.models.dto;

public class CodebookItemDTO {
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
