package agentbackend.agentback.controller;

import agentbackend.agentback.controller.dto.*;
import agentbackend.agentback.model.Price;
import agentbackend.agentback.model.PriceList;
import agentbackend.agentback.service.DiscountService;
import agentbackend.agentback.service.PriceService;
import agentbackend.agentback.service.PricelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PriceController {

    @Autowired
    private PriceService priceService;
    @Autowired
    private PricelistService pricelistService;
    @Autowired
    private DiscountService discountService;

    //PRICELIST

    // Kreiranje pricelista
    @PostMapping(value = "/pricelist")
    public ResponseEntity<PricelistDTO> createPricelist(@RequestBody PricelistDTO pricelist, Principal user) {
        return pricelistService.createPricelist(pricelist,user.getName());
    }

    // Brisanje pricelista
    @DeleteMapping(value = "/pricelist/{id}")
    public ResponseEntity<String> deletePricelist(@PathVariable("id") Long id, Principal user) {
        return pricelistService.deletePricelist(id, user.getName());
    }

    @GetMapping(value = "/pricelist")
    public ResponseEntity<List<PricelistDTO>> getAllPriceLists(Principal user){
        List<PricelistDTO> priceLists = pricelistService.getPriceLists(user.getName());
        return ResponseEntity.status(HttpStatus.OK).body(priceLists);
    }

    @GetMapping(value = "/pricelist/{id}")
    public ResponseEntity<PricelistDTO> getAllPriceList(@PathVariable("id") Long id, Principal user){
        PricelistDTO priceLists = pricelistService.getPriceList(id, user.getName());
        return ResponseEntity.status(HttpStatus.OK).body(priceLists);
    }

    @GetMapping(value = "/pricelist/ad/{id}")
    public ResponseEntity<Long> getAllPriceListId(@PathVariable("id") Long id, Principal user){
        Long idd = pricelistService.getPriceListId(id, user.getName());
        if(idd != null)
            return ResponseEntity.status(HttpStatus.OK).body(idd);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // PRICE

    // Kreiranje pricea
    @PostMapping(value = "/price")
    public ResponseEntity<String> createPrice(@RequestBody PriceDTO priceDTO, Principal user) {
        boolean response = priceService.createPrice(priceDTO, user.getName());
        if (response)
            return ResponseEntity.ok("Price successfully created!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }

    // Editovanje pricea
    @PutMapping(value = "/price/{id}")
    public ResponseEntity<String> editPrice(@PathVariable("id") Long id, @RequestBody PriceDTO priceDTO, Principal user) {
        boolean response = priceService.editPrice(id,priceDTO,user.getName());
        if (response)
            return ResponseEntity.ok("Price successfully updated!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }

    // Brisanje pricea
    @DeleteMapping(value = "/price/{id}/{pricelistId}")
    public ResponseEntity<String> deletePrice(@PathVariable("id") Long id, @PathVariable("pricelistId") Long pricelistId, Principal user){
        boolean response = priceService.deletePrice(id,pricelistId,user.getName());
        if (response)
            return ResponseEntity.ok("Price successfully deleted!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }

    @GetMapping(value = "/price/{id}")
    public ResponseEntity<Price2DTO> getPrice(@PathVariable("id") Long id, Principal user){
        Price2DTO price = priceService.getPrice(id, user.getName());
        return ResponseEntity.status(HttpStatus.OK).body(price);
    }

    @GetMapping(value = "/discount/{id}")
    public ResponseEntity<Price2DTO> getPricesDiscounts(@PathVariable("id") Long id, Principal user){
        Price2DTO price = priceService.getPricesDisounts(id, user.getName());
        return ResponseEntity.status(HttpStatus.OK).body(price);
    }

    // DISCOUNT

    // Kreiranje discounta
    @PostMapping(value = "/discount")
    public ResponseEntity<String> createDiscount(@RequestBody DiscountDTO discountDTO, Principal user) {
        boolean response = discountService.createDiscount(discountDTO, user.getName());
        if (response)
            return ResponseEntity.ok("Disocunt successfully created!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }


    // Brisanje discounta
    @DeleteMapping(value = "/discount/{id}/{priceId}")
    public ResponseEntity<String> deleteDiscount(@PathVariable("id") Long id, @PathVariable("priceId") Long priceId, Principal user){
        boolean response = discountService.deleteDiscount(id, priceId, user.getName());
        if (response)
            return ResponseEntity.ok("Discount successfully deleted!");
        else
            return ResponseEntity.status(400).body("Could not accept");

    }


    // GETTERS

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
            priceListDTO.setCarId(price.getCar().getId());
            priceListDTO.setDiscountPercentage(price.getDiscounts().get(0).getPercentage());
            priceListDTO.setMinDays(price.getDiscounts().get(0).getMinDays());
            priceListDTO.setPrice(price.getPrice());
            priceListDTO.setPriceKm(price.getPriceKm());
            priceListDTO.setId(price.getId());
            return ResponseEntity.status(200).body(priceListDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

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


    //Ovo koristim u pretrazi
    @GetMapping(value = "/test/pricelist/{ID}/car/{id}")
    public ResponseEntity<Double> getCarPriceFromPriceList(@PathVariable("ID") Long priceListId, @PathVariable("id") Long carId){
        Double price = priceService.getPriceFromPriceListAndCars(priceListId, carId);
        return ResponseEntity.status(200).body(price);
    }
}
