package CarsAdsApp.controller;

import CarsAdsApp.model.Price;
import CarsAdsApp.model.PriceList;
import CarsAdsApp.model.dto.AdDTO;
import CarsAdsApp.model.dto.PriceDTO;
import CarsAdsApp.model.dto.PriceListDTO;
import CarsAdsApp.service.PriceService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping(value = "/test/price")
    public ResponseEntity<ArrayList<Price>> getAllPrices(){
        ArrayList<Price> prices = priceService.getAllPrices();
        return ResponseEntity.status(HttpStatus.OK).body(prices);
    }

    @GetMapping(value = "/test/pricelist")
    public ResponseEntity<ArrayList<PriceList>> getAllPriceLists(){
        ArrayList<PriceList> priceLists = priceService.getPriceLists();
        return ResponseEntity.status(HttpStatus.OK).body(priceLists);
    }

    // Kreiranje price
    @PostMapping(value = "/test/price")
    public ResponseEntity<String> createAd(@RequestBody PriceDTO priceDTO) throws JSONException {
        boolean response = priceService.createPrice(priceDTO);
        if (response)
            return ResponseEntity.ok("Price successfully created!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }

    @GetMapping(value ="/test/{id}")
    public ResponseEntity<Double> getPriceByCarId(@PathVariable("id") Long carId){
        Double price = priceService.getPriceByCarId(carId);
        return ResponseEntity.status(200).body(price);
    }

    @GetMapping(value = "/test/pricelist/car/{id}")
    public ResponseEntity<PriceListDTO> getCarPriceFromPriceList(@PathVariable("id") Long carId){
        Price price = priceService.getCarPriceFromPriceList(carId);
        if(price != null){
            PriceListDTO priceListDTO = new PriceListDTO();
            priceListDTO.setCarId(price.getCarId());
            priceListDTO.setDiscountPercentage(price.getDiscounts().get(0).getPercentage());
            priceListDTO.setMinDays(price.getDiscounts().get(0).getMinDays());
            priceListDTO.setPrice(price.getPrice());
            priceListDTO.setPriceKm(price.getPriceKm());
            return ResponseEntity.status(200).body(priceListDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //Ovo koristim u pretrazi
    @GetMapping(value = "/test/pricelist/{ID}/car/{id}")
    public ResponseEntity<Double> getCarPriceFromPriceList(@PathVariable("ID") Long priceListId, @PathVariable("id") Long carId){
      Double price = priceService.getPriceFromPriceListAndCars(priceListId, carId);
      return ResponseEntity.status(200).body(price);
    }
}
