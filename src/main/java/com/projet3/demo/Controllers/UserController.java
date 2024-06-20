package com.projet3.demo.Controllers;

import com.projet3.demo.Exceptions.ResourceNotFoundException;
import com.projet3.demo.Models.User;
import com.projet3.demo.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Tag(name = "User Controller")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Récupère tous les utilisateurs", description = "Récupère tous les utilisateurs présent dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateurs trouvés"),
            @ApiResponse(responseCode = "404", description = "Utilisateurs inconnus")
    })
    @GetMapping("/users*")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @Operation(summary = "Récupère un utilisateur", description = "Récupère les infos d'un utilisateur en fonction de son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur inconnu")
    })
    @GetMapping("/user*/{id}")
    public User getUserByID(@PathVariable(value = "id") Long UserID) throws ResourceNotFoundException {
        User user = userService.findByID(UserID);
        user.setPassword(null);

        return user;
    }

    @Operation(summary = "Supprime un utilisateur", description = "Supprime un utilisateur en fonction de son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable(value = "id") Long UserID) throws ResourceNotFoundException {
        userService.deleteByID(UserID);
    }

    @Operation(summary = "Mettre à jour un utilisateur", description = "Mettre à jour les informations d'un utilisateur en fonction de son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Échec de la mise à jour de l'utilisateur"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable(value = "id") Long UserID,
                                 @Validated @RequestBody User userDetails)throws ResourceNotFoundException{
        return userService.updateUserByID(UserID, userDetails);
    }

    @Operation(summary = "Création d'un utilisateur", description = "Permet la création d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Échec de la création de l'utilisateur")
    })
    @PostMapping("/users*")
    public User createUser(@Validated @RequestBody User user) {
        return userService.createUser(user);
    }


}
