package carRent.model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base64", unique = false, nullable = false)
    private String base64;

    public Image() {
    }

    public Image(String base64) {
        this.base64 = base64;
    }

    public Long getId() {
        return id;
    }

    public String getBase64() {
        return base64;
    }
}
