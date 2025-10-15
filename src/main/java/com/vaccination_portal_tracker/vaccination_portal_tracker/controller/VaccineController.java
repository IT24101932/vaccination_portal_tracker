package com.vaccination_portal_tracker.vaccination_portal_tracker.controller;

import com.vaccination_portal_tracker.vaccination_portal_tracker.model.Vaccine;
import com.vaccination_portal_tracker.vaccination_portal_tracker.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccines")
@CrossOrigin(origins = "http://localhost:3000")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping
    public ResponseEntity<List<Vaccine>> getAllVaccines() {
        try {
            List<Vaccine> vaccines = vaccineService.getAllVaccines();
            return new ResponseEntity<>(vaccines, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaccine> getVaccineById(@PathVariable("id") Long id) {
        try {
            Vaccine vaccine = vaccineService.getVaccineById(id);
            return new ResponseEntity<>(vaccine, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Vaccine> createVaccine(@RequestBody Vaccine vaccine) {
        try {
            Vaccine newVaccine = vaccineService.createVaccine(vaccine);
            return new ResponseEntity<>(newVaccine, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaccine> updateVaccine(@PathVariable("id") Long id, @RequestBody Vaccine vaccine) {
        try {
            Vaccine updatedVaccine = vaccineService.updateVaccine(id, vaccine);
            return new ResponseEntity<>(updatedVaccine, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteVaccine(@PathVariable("id") Long id) {
        try {
            vaccineService.deleteVaccine(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}