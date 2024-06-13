package com.projet3.demo.Controllers;

import com.projet3.demo.Exceptions.ResourceNotFoundException;
import com.projet3.demo.Models.Rental;
import com.projet3.demo.Models.RentalDTO;
import com.projet3.demo.Services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/rentals")
    public ResponseEntity<Map<String, List<Rental>>> getRentals() {
        Map<String, List<Rental>> rentals = new HashMap<>();
        rentals.put("rentals", rentalService.findAll());

        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/rentals*/{id}")
    public Rental getRentalByID(@PathVariable(value = "id") Long RentalID) throws ResourceNotFoundException {
        return rentalService.findByID(RentalID);
    }

    @DeleteMapping("/rentals/{id}")
    public void deleteRental(@PathVariable(value = "id") Long RentalID) throws ResourceNotFoundException {
        rentalService.deleteByID(RentalID);
    }

    @PutMapping("/rentals/{id}")
    public Rental updateRental(@PathVariable(value = "id") Long RentalID,
                               @Validated @ModelAttribute RentalDTO rental) throws ResourceNotFoundException, IOException {
        return rentalService.updateRentalByID(RentalID, rental);
    }

    @PostMapping("/rentals")
    public Rental createRental(@Validated @ModelAttribute RentalDTO rental) throws IOException {
        return rentalService.createRental(rental);
    }


}
