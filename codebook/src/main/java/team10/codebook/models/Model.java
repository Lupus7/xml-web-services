package team10.codebook.models;

import javax.persistence.*;

@Entity
@Table(name = "model")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "model_id_seq_gen")
    @SequenceGenerator(name="model_id_seq_gen", sequenceName = "model_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="brand_id", nullable=false)
    private Brand brand;

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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
