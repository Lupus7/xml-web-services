package agentbackend.agentback.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class BundleDTO {

    private List<BookDTO> books = new ArrayList<>();

    // Samo kod samostalnog zauzeca se koristi
    private String loaner;

    public BundleDTO(List<BookDTO> books) {
        this.books = books;
    }

    public BundleDTO(List<BookDTO> books, String loaner) {
        this.books = books;
        this.loaner = loaner;
    }

    public BundleDTO() {
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public String getLoaner() {
        return loaner;
    }

    public void setLoaner(String loaner) {
        this.loaner = loaner;
    }
}
