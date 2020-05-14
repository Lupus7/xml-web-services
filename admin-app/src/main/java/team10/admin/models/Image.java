package team10.admin.models;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base64", unique = false, nullable = false, length = 100000)
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
