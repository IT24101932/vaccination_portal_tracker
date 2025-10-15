package com.vaccination_portal_tracker.vaccination_portal_tracker.controller;

import com.vaccination_portal_tracker.vaccination_portal_tracker.model.VaccineBatch;
import com.vaccination_portal_tracker.vaccination_portal_tracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "http://localhost:3000")
public class StockController {

    @Autowired
    private StockService stockService;

    // Add new stock entry
    @PostMapping
    public ResponseEntity<VaccineBatch> addStockEntry(@RequestBody VaccineBatch vaccineBatch) {
        try {
            VaccineBatch newBatch = stockService.addStockEntry(vaccineBatch);
            return new ResponseEntity<>(newBatch, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // View all available stock levels
    @GetMapping
    public ResponseEntity<List<VaccineBatch>> getAllStockEntries() {
        try {
            List<VaccineBatch> stockEntries = stockService.getAllStockEntries();
            return new ResponseEntity<>(stockEntries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get stock by ID
    @GetMapping("/{id}")
    public ResponseEntity<VaccineBatch> getStockById(@PathVariable("id") Long id) {
        try {
            VaccineBatch batch = stockService.getStockById(id);
            return new ResponseEntity<>(batch, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Adjust stock quantities
    @PutMapping("/{id}/quantity")
    public ResponseEntity<VaccineBatch> updateStockQuantity(@PathVariable("id") Long id, @RequestParam Integer quantityUsed) {
        try {
            VaccineBatch updatedBatch = stockService.updateStockQuantity(id, quantityUsed);
            return new ResponseEntity<>(updatedBatch, HttpStatus.OK);
        } catch (IllegalArgumentException e) { // specific exception first
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) { // general exception next
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update stock entry
    @PutMapping("/{id}")
    public ResponseEntity<VaccineBatch> updateStockEntry(@PathVariable("id") Long id, @RequestBody VaccineBatch vaccineBatch) {
        try {
            VaccineBatch updatedBatch = stockService.updateStockEntry(id, vaccineBatch);
            return new ResponseEntity<>(updatedBatch, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Remove stock entry
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStockEntry(@PathVariable("id") Long id) {
        try {
            stockService.deleteStockEntry(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get stock by vaccine type
    @GetMapping("/vaccine/{vaccineId}")
    public ResponseEntity<List<VaccineBatch>> getStockByVaccine(@PathVariable("vaccineId") Long vaccineId) {
        try {
            List<VaccineBatch> batches = stockService.getStockByVaccineId(vaccineId);
            return new ResponseEntity<>(batches, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get expired stocks
    @GetMapping("/expired")
    public ResponseEntity<List<VaccineBatch>> getExpiredStocks() {
        try {
            List<VaccineBatch> expiredBatches = stockService.getExpiredStocks();
            return new ResponseEntity<>(expiredBatches, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get available stocks (current stock > 0)
    @GetMapping("/available")
    public ResponseEntity<List<VaccineBatch>> getAvailableStocks() {
        try {
            List<VaccineBatch> availableBatches = stockService.getAvailableStocks();
            return new ResponseEntity<>(availableBatches, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
