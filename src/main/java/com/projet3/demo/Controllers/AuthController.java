package com.projet3.demo.Controllers;


import com.projet3.demo.Exceptions.ResourceNotFoundException;
import com.projet3.demo.Models.User;
import com.projet3.demo.Services.JWTService;
import com.projet3.demo.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Auth Controller")
public class AuthController {
    public AuthController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    private UserService userService;
    private JWTService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

/*    @PostMapping("/login")
    public String getToken(Authentication authentication) {
        String token = jwtService.generateToken(authentication);
        return token;
    }*/


    @Operation(summary = "Envoi les données d'inscription", description = "Permet à un utilisateur de s'enregistrer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enregistrement effectué avec succès"),
            @ApiResponse(responseCode = "400", description = "Échec de l'enregistrement")
    })
    @PostMapping ("/auth/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        String token = jwtService.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, String> tokenMAP = new HashMap<>();
        tokenMAP.put("token", token);

        User newUser = userService.createUser(user);
        // return ResponseEntity.ok(newUser);
        return ResponseEntity.ok(tokenMAP);
    }

    @Operation(summary = "Envoi les données de connexion", description = "Permet à un utilisateur de se connecter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur connecté avec succès"),
            @ApiResponse(responseCode = "400", description = "Échec de la connexion")
    })
    @PostMapping ("/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        UserDetails userLogin = null;
        boolean passwordOk = false;


        try {
            userLogin = userService.loadUserByUsername(email);
            passwordOk = passwordEncoder.matches(password, userLogin.getPassword());


        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (passwordOk) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
                    String token = jwtService.generateToken(authentication);

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    Map<String, String> tokenMAP = new HashMap<>();
                    tokenMAP.put("token", token);
                    return ResponseEntity.ok(tokenMAP);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Récupère les infos de la personne connectée", description = "Récupère les infos de la personne connectée en fonction de l'ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informations récupérées avec succès"),
            @ApiResponse(responseCode = "400", description = "Informations non récupérées")
    })
    @GetMapping ("/auth/me")
    public ResponseEntity<User> getUserByID() throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return ResponseEntity.ok(userService.findByEmail(email));
    }

}
