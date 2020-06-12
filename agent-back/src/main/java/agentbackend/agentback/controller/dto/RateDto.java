package agentbackend.agentback.controller.dto;

public class RateDto {
    private int rate;
    private String comment;
    private long booking;

    public RateDto(){

    }

    public RateDto(int rate, String comment, long booking) {
        this.rate = rate;
        this.comment = comment;
        this.booking = booking;
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

    public void setBooking(long booking) {
        this.booking = booking;
    }
}
