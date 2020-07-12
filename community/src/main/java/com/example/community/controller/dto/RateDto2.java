package com.example.community.controller.dto;

import com.example.community.model.Rate;

public class RateDto2 {
    private int rate;
    private String comment;
    private Long booking;
    private Long carId;
    private Long id;
    private boolean approved;
    private String user;
    private  String recomment;

    public RateDto2() {

    }

    public RateDto2(int rate, String comment, Long booking, Long carId) {
        this.rate = rate;
        this.comment = comment;
        this.booking = booking;
        this.carId = carId;
    }

    public RateDto2(Rate rate) {
        this.id = rate.getId();
        this.carId = rate.getCarId();
        this.rate = rate.getRate();
        this.comment = rate.getComment();
        this.booking = rate.getBooking();
        this.approved = rate.isApproved();
        this.user = rate.getRater();
        this.recomment = rate.getRecomment();
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getBooking() {
        return booking;
    }

    public void setBooking(Long booking) {
        this.booking = booking;
    }


    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRecomment() {
        return recomment;
    }

    public void setRecomment(String recomment) {
        this.recomment = recomment;
    }
}
