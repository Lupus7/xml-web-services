package CarsAdsApp.model;

import javax.persistence.*;

@Entity
public class Image {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "encoded64_image")
    private String Encoded64Image;
    @Column(name = "car_id")
    private Long CarId;

    public Image(){

    }

    public Image(String encoded64Image, Long carId) {
        Encoded64Image = encoded64Image;
        CarId = carId;
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
        return CarId;
    }

    public void setCarId(Long carId) {
        CarId = carId;
    }
}
