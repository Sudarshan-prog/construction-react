package com.engineersveedu.backend.controller;

import com.engineersveedu.backend.model.Contractor;
import com.engineersveedu.backend.repository.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/contractors")
@CrossOrigin(origins = "*")
public class ContractorController {

    @Autowired
    private ContractorRepository contractorRepository;

    @GetMapping
    public ResponseEntity<List<Contractor>> searchContractors(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) Double minRating) {
        
        List<Contractor> contractors = contractorRepository.searchContractors(query, city, specialization, minRating);
        return ResponseEntity.ok(contractors);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Contractor>> getFeaturedContractors() {
        List<Contractor> featured = contractorRepository.findByVerifiedTrue();
        // If no verified found, return first few
        if (featured.isEmpty()) {
            featured = contractorRepository.findAll().subList(0, Math.min(3, (int) contractorRepository.count()));
        }
        return ResponseEntity.ok(featured);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContractorById(@PathVariable Long id) {
        Optional<Contractor> contractorOpt = contractorRepository.findById(id);
        if (contractorOpt.isPresent()) {
            return ResponseEntity.ok(contractorOpt.get());
        }

        Map<String, String> error = new HashMap<>();
        error.put("error", "Contractor not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
