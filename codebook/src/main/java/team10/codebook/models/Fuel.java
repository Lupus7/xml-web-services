package team10.codebook.models;

import javax.persistence.*;

@Entity
@Table(name = "fuel")
public class Fuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fuel_id_seq")
    private Long id;

    @Column(name = "name", unique = false, nullable = false)
    private String type;

    public Fuel() {
    }

    public Fuel(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
