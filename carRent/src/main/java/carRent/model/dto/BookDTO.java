package carRent.model.dto;

import com.car_rent.agent_api.BookDetails;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;

public class BookDTO {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String place;

    private Long adId;

    public BookDTO() {
    }

    public BookDTO(LocalDateTime startDate, LocalDateTime endDate, String place, Long adId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.adId = adId;
    }

    public LocalDateTime convert(XMLGregorianCalendar date) {

        return LocalDateTime.of(
                date.getYear(),
                date.getMonth(),
                date.getDay(), 0, 0, 0);

    }

    public BookDTO(BookDetails bookDetails) {
        this.startDate = convert(bookDetails.getStartDate());
        this.endDate = convert(bookDetails.getEndDate());
        this.place = bookDetails.getPlace();
        this.adId = bookDetails.getAdId();
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

}
