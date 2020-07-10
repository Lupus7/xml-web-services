package team10.codebook.controllers;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team10.codebook.models.dto.CodebookItemDTO;
import team10.codebook.services.CodebookService;

import java.util.List;

@RestController
public class CodebookController {
    @Autowired
    CodebookService codebookService;

    @GetMapping("/brand")
    @PreAuthorize("hasAuthority('READ_CODEBOOK')")
    public ResponseEntity<List<CodebookItemDTO>> getAllBrands() {
        List<CodebookItemDTO> retVal = codebookService.getAllBrands();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.status(408).body(null);
    }

    @PostMapping("/brand")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> addBrand(@RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.addBrand(codebookItemDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/brand/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> updateBrand(@PathVariable("id") Long id, @RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.updateBrand(codebookItemDTO, id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/brand/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> deleteBrand(@PathVariable("id") Long id) {
        if (codebookService.deleteBrand(id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/model")
    @PreAuthorize("hasAuthority('READ_CODEBOOK')")
    public ResponseEntity<List<CodebookItemDTO>> getAllModels() {
        List<CodebookItemDTO> retVal = codebookService.getAllModels();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.status(408).body(null);
    }

    @PostMapping("/model")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> addModel(@RequestBody String codebookItemDTO) throws JSONException {
        if (codebookService.addModel(codebookItemDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/model/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> updateModel(@PathVariable("id") Long id, @RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.updateModel(codebookItemDTO, id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/model/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> deleteModel(@PathVariable("id") Long id) {
        if (codebookService.deleteModel(id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/class")
    @PreAuthorize("hasAuthority('READ_CODEBOOK')")
    public ResponseEntity<List<CodebookItemDTO>> getAllClasses() {
        List<CodebookItemDTO> retVal = codebookService.getAllClasses();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.status(408).body(null);
    }

    @PostMapping("/class")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> addClass(@RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.addCarClass(codebookItemDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/class/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> updateClass(@PathVariable("id") Long id, @RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.updateCarClass(codebookItemDTO, id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/class/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> deleteClass(@PathVariable("id") Long id) {
        if (codebookService.deleteCarClass(id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/fuel")
    @PreAuthorize("hasAuthority('READ_CODEBOOK')")
    public ResponseEntity<List<CodebookItemDTO>> getAllFuels() {
        List<CodebookItemDTO> retVal = codebookService.getAllFuels();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.status(408).body(null);
    }

    @PostMapping("/fuel")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> addFuel(@RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.addFuel(codebookItemDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/fuel/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> updateFuel(@PathVariable("id") Long id, @RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.updateFuel(codebookItemDTO, id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/fuel/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> deleteFuel(@PathVariable("id") Long id) {
        if (codebookService.deleteFuel(id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/transmission")
    @PreAuthorize("hasAuthority('READ_CODEBOOK')")
    public ResponseEntity<List<CodebookItemDTO>> getAllTransmissions() {
        List<CodebookItemDTO> retVal = codebookService.getAllTransmissions();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.status(408).body(null);
    }

    @PostMapping("/transmission")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> addTransmission(@RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.addTransmission(codebookItemDTO))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/transmission/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> updateTransmission(@PathVariable("id") Long id, @RequestBody CodebookItemDTO codebookItemDTO) {
        if (codebookService.updateTransmission(codebookItemDTO, id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/transmission/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CODEBOOK')")
    public ResponseEntity<String> deleteTransmission(@PathVariable("id") Long id) {
        if (codebookService.deleteTransmission(id))
            return ResponseEntity.ok("Operation successful!");
        return ResponseEntity.badRequest().body(null);
    }
}
