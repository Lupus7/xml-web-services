package agentbackend.agentback.controller.dto;

import java.time.LocalDateTime;

public class MessageDto2 {

    protected String body;
    protected LocalDateTime date;
    protected long booking;
    private String receiver;
    private String sender;
    private Long id;

    public MessageDto2() {
    }

    public MessageDto2(String body, LocalDateTime date, long booking) {
        this.body = body;
        this.date = date;
        this.booking = booking;
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

    public long getBooking() {
        return booking;
    }

    public void setBooking(long booking) {
        this.booking = booking;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
