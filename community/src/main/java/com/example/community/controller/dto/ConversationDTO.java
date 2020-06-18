package com.example.community.controller.dto;

public class ConversationDTO {

    private String receiver;
    private Long bookingId;

    public ConversationDTO() {
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
