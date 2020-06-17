package agentbackend.agentback.controller.dto;

import java.util.Date;

public class MessageDto {

    protected String body;
    protected String date;
    protected long booking;
    protected String sender;
    protected String receiver;

    public MessageDto() {
    }

    public MessageDto(String body, String date, long booking, String sender, String receiver) {
        this.body = body;
        this.date = date;
        this.booking = booking;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getBooking() {
        return booking;
    }

    public void setBooking(long booking) {
        this.booking = booking;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
