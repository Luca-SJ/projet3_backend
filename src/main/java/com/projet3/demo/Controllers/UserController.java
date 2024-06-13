package com.projet3.demo.Controllers;

import com.projet3.demo.Exceptions.ResourceNotFoundException;
import com.projet3.demo.Models.User;
import com.projet3.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users*")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/user*/{id}")
    public User getUserByID(@PathVariable(value = "id") Long UserID) throws ResourceNotFoundException {
        return userService.findByID(UserID);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable(value = "id") Long UserID) throws ResourceNotFoundException {
        userService.deleteByID(UserID);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable(value = "id") Long UserID,
                                 @Validated @RequestBody User userDetails)throws ResourceNotFoundException{
        return userService.updateUserByID(UserID, userDetails);
    }

    @PostMapping("/users*")
    public User createUser(@Validated @RequestBody User user) {
        return userService.createUser(user);
    }


}
