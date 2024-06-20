package com.projet3.demo.Controllers;

import com.projet3.demo.Exceptions.ResourceNotFoundException;
import com.projet3.demo.Models.Rental;
import com.projet3.demo.Models.RentalDTO;
import com.projet3.demo.Services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Rental Controller")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Operation(summary = "Récupère tous les rentals", description = "Récupère tous les rentals présent dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals trouvés"),
            @ApiResponse(responseCode = "404", description = "Rentals inconnus")
    })
    @GetMapping("/rentals")
    public ResponseEntity<Map<String, List<Rental>>> getRentals() {
        Map<String, List<Rental>> rentals = new HashMap<>();
        rentals.put("rentals", rentalService.findAll());

        return ResponseEntity.ok(rentals);
    }

    @Operation(summary = "Récupère un rental", description = "Récupère les infos d'un rental en fonction de son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental trouvé"),
            @ApiResponse(responseCode = "404", description = "Rental inconnu")
    })
    @GetMapping("/rentals*/{id}")
    public Rental getRentalByID(@PathVariable(value = "id") Long RentalID) throws ResourceNotFoundException {
        return rentalService.findByID(RentalID);
    }

    @Operation(summary = "Supprime un rental", description = "Supprime un rental en fonction de son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Rental non trouvé")
    })
    @DeleteMapping("/rentals/{id}")
    public void deleteRental(@PathVariable(value = "id") Long RentalID) throws ResourceNotFoundException {
        rentalService.deleteByID(RentalID);
    }

    @Operation(summary = "Mettre à jour un rental", description = "Mettre à jour les informations d'un rental en fonction de son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Échec de la mise à jour du Rental"),
            @ApiResponse(responseCode = "404", description = "Rental non trouvé")
    })
    @PutMapping("/rentals/{id}")
    public Rental updateRental(@PathVariable(value = "id") Long RentalID,
                               @Validated @ModelAttribute RentalDTO rental) throws ResourceNotFoundException, IOException {
        return rentalService.updateRentalByID(RentalID, rental);
    }

    @Operation(summary = "Création d'un rental", description = "Permet la création d'un rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Échec de la création du Rental")
    })
    @PostMapping("/rentals")
    public Rental createRental(@Validated @ModelAttribute RentalDTO rental) throws IOException {
        return rentalService.createRental(rental);
    }


}
