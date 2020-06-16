package CarsAdsApp.model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_id_seq_gen")
    @SequenceGenerator(name="image_id_seq_gen", sequenceName = "image_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "encoded64_image",columnDefinition="text")
    private String encoded64Image;
    @Column(name = "car_id")
    private Long carId;

    public Image(){

    }

    public Image(String encoded64Image, Long carId) {
        this.encoded64Image = encoded64Image;
        this.carId = carId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEncoded64Image() {
        return this.encoded64Image;
    }

    public void setEncoded64Image(String encoded64Image) {
        this.encoded64Image = encoded64Image;
    }

    public Long getCarId() {
        return this.carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
