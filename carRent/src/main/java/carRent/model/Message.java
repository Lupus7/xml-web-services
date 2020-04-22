package carRent.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body", unique = false, nullable = false)
    private String body;

    @Column(name = "date", unique = false, nullable = false)
    private LocalDateTime date;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="booking_id",referencedColumnName = "id")
    private Booking booking;

    public Message() {
    }

    public Message(String body, LocalDateTime date, Booking booking) {
        this.body = body;
        this.date = date;
        this.booking = booking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
