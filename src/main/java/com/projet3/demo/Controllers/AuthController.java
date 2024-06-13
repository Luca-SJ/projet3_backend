package com.projet3.demo.Controllers;


import com.projet3.demo.Exceptions.ResourceNotFoundException;
import com.projet3.demo.Models.User;
import com.projet3.demo.Services.JWTService;
import com.projet3.demo.Services.UserService;
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

    @GetMapping ("/auth/me")
    public ResponseEntity<User> getUserByID() throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return ResponseEntity.ok(userService.findByEmail(email));
    }

}
