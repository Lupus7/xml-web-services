package com.example.community.controller.dto;

import java.util.Date;

public class MessageDto {

    protected String body;
    protected Date date;
    protected long booking;

    public MessageDto() {
    }

    public MessageDto(String body, Date date, long booking) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getBooking() {
        return booking;
    }

    public void setBooking(long booking) {
        this.booking = booking;
    }
}
