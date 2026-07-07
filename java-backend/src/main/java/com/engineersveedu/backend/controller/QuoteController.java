package com.engineersveedu.backend.controller;

import com.engineersveedu.backend.model.Quote;
import com.engineersveedu.backend.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quotes")
@CrossOrigin(origins = "*")
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;

    @PostMapping
    public ResponseEntity<?> submitQuote(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");
        String message = request.get("message");
        String projectType = request.getOrDefault("projectType", "General");

        if (name == null || email == null || message == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name, email, and message are required fields");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        Quote quote = new Quote(name, email, message, projectType, "New Inquiry");
        quoteRepository.save(quote);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Quote request submitted successfully");
        response.put("quoteId", quote.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Quote>> getAllQuotes(@RequestParam(required = false) String email) {
        if (email != null && !email.isEmpty()) {
            return ResponseEntity.ok(quoteRepository.findByEmail(email));
        }
        return ResponseEntity.ok(quoteRepository.findAllByOrderByCreatedAtDesc());
    }
}
