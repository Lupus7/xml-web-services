package CarsAdsApp.controller.dto;

import CarsAdsApp.model.Image;

import java.util.ArrayList;

public class ImageDTO {
    private ArrayList<String> images;

    public ImageDTO(){

    }

    public ImageDTO(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
