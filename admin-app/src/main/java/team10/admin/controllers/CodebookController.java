package team10.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.admin.models.dto.CodebookItemDTO;
import team10.admin.services.CodebookService;

import java.util.List;

@RestController
@RequestMapping("codebook")
public class CodebookController {
    @Autowired
    CodebookService codebookService;

    @GetMapping("/brand")
    public ResponseEntity<List<CodebookItemDTO>> getAllBrands() {
        List<CodebookItemDTO> retVal = codebookService.getAllBrands();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.status(408).body(null);
    }

    @PostMapping("/brand")
    public ResponseEntity<String> addBrand(@RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.addBrand(codebookItemDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/brand/{id}")
    public ResponseEntity<String> updateBrand(@PathVariable("id") Long id, @RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.updateBrand(codebookItemDTO, id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/brand/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable("id") Long id) {
        if (codebookService.deleteBrand(id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/model")
    public ResponseEntity<List<CodebookItemDTO>> getAllModels() {
        List<CodebookItemDTO> retVal = codebookService.getAllModels();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.status(408).body(null);
    }

    @PostMapping("/model")
    public ResponseEntity<String> addModel(@RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.addModel(codebookItemDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/model/{id}")
    public ResponseEntity<String> updateModel(@PathVariable("id") Long id, @RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.updateModel(codebookItemDTO, id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/model/{id}")
    public ResponseEntity<String> deleteModel(@PathVariable("id") Long id) {
        if (codebookService.deleteModel(id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/class")
    public ResponseEntity<List<CodebookItemDTO>> getAllClasses() {
        List<CodebookItemDTO> retVal = codebookService.getAllClasses();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.status(408).body(null);
    }

    @PostMapping("/class")
    public ResponseEntity<String> addClass(@RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.addCarClass(codebookItemDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/class/{id}")
    public ResponseEntity<String> updateClass(@PathVariable("id") Long id, @RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.updateCarClass(codebookItemDTO, id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/class/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable("id") Long id) {
        if (codebookService.deleteCarClass(id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/fuel")
    public ResponseEntity<List<CodebookItemDTO>> getAllFuels() {
        List<CodebookItemDTO> retVal = codebookService.getAllFuels();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.status(408).body(null);
    }

    @PostMapping("/fuel")
    public ResponseEntity<String> addFuel(@RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.addFuel(codebookItemDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/fuel/{id}")
    public ResponseEntity<String> updateFuel(@PathVariable("id") Long id, @RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.updateFuel(codebookItemDTO, id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/fuel/{id}")
    public ResponseEntity<String> deleteFuel(@PathVariable("id") Long id) {
        if (codebookService.deleteFuel(id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/transmission")
    public ResponseEntity<List<CodebookItemDTO>> getAllTransmissions() {
        List<CodebookItemDTO> retVal = codebookService.getAllTransmissions();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.status(408).body(null);
    }

    @PostMapping("/transmission")
    public ResponseEntity<String> addTransmission(@RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.addTransmission(codebookItemDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/transmission/{id}")
    public ResponseEntity<String> updateTransmission(@PathVariable("id") Long id, @RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.updateTransmission(codebookItemDTO, id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/transmission/{id}")
    public ResponseEntity<String> deleteTransmission(@PathVariable("id") Long id) {
        if (codebookService.deleteTransmission(id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }
}
