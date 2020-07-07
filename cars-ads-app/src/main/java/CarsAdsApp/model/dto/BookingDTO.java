package CarsAdsApp.model.dto;

import CarsAdsApp.model.RequestState;

import java.time.LocalDateTime;

public class BookingDTO {

    Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private RequestState state;

    private String place;

    private LocalDateTime created;

    private Long ad;

    private String advertiser;

    public BookingDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Long getAd() {
        return ad;
    }

    public void setAd(Long ad) {
        this.ad = ad;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }
}
