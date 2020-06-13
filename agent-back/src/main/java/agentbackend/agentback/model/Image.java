package agentbackend.agentback.model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_id_seq")
    private Integer Id;
    @Column(name = "encoded64_image",columnDefinition="text")
    private String Encoded64Image;
    @Column(name = "car_id")
    private Long carId;

    public Image(){

    }

    public Image(String encoded64Image, Long carId) {
        Encoded64Image = encoded64Image;
        this.carId = carId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getEncoded64Image() {
        return Encoded64Image;
    }

    public void setEncoded64Image(String encoded64Image) {
        Encoded64Image = encoded64Image;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        carId = carId;
    }
}
