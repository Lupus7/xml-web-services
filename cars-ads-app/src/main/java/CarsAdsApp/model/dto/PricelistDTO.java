package CarsAdsApp.model.dto;

import java.util.ArrayList;
import java.util.List;

public class PricelistDTO {

    private String name;
    private Long id;
    private String owner;
    private List<Price2DTO> prices = new ArrayList<>();

    public PricelistDTO() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Price2DTO> getPrices() {
        return prices;
    }

    public void setPrices(List<Price2DTO> prices) {
        this.prices = prices;
    }
}
