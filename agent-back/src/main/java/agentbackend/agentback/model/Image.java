package agentbackend.agentback.model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_id_seq_gen")
    @SequenceGenerator(name="image_id_seq_gen", sequenceName = "image_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "encoded64_image",columnDefinition="text")
    private String encoded64Image;

    @Column()
    private Long serviceId;

    @ManyToOne
    @JoinColumn(name = "car", nullable = false)
    private Car car;



    public Image(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEncoded64Image() {
        return encoded64Image;
    }

    public void setEncoded64Image(String encoded64Image) {
        this.encoded64Image = encoded64Image;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
