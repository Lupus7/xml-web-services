package team10.codebook.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_id_seq_gen")
    @SequenceGenerator(name="brand_id_seq_gen", sequenceName = "brand_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Model> models;


    public Brand() {
        this.models = new ArrayList<>();
    }

    public Brand(String name) {
        this.name = name;
        this.models = new ArrayList<>();
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

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}
