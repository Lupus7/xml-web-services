package team10.codebook.models;

import javax.persistence.*;

@Entity
@Table(name = "model")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "model_id_seq")
    private Long id;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    public Model() {
    }

    public Model(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
