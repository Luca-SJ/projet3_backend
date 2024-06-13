package com.projet3.demo.Services;

import com.projet3.demo.Exceptions.ResourceNotFoundException;
import com.projet3.demo.Models.User;
import com.projet3.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByID(Long UserID) throws ResourceNotFoundException {
        return userRepository.findById(UserID)
                .orElseThrow(()->new ResourceNotFoundException("utilisateur avec ID : " + UserID + " inexistant"));
    }

    public User findByEmail(String UserEmail) throws ResourceNotFoundException {
        return userRepository.findByEmail(UserEmail)
                .orElseThrow(()->new ResourceNotFoundException("utilisateur avec ID : " + UserEmail + " inexistant"));
    }

    public void deleteByID(Long UserID) throws ResourceNotFoundException {
        User user = userRepository.findById(UserID)
                .orElseThrow(()->new ResourceNotFoundException("utilisateur avec ID : " + UserID + " inexistant"));
        userRepository.delete(user);
    }

    public User updateUserByID(Long UserID, User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(UserID)
                .orElseThrow(()->new ResourceNotFoundException("utilisateur avec ID : "+ UserID + " inexistant"));

        user.setName(userDetails.getName());
        user.setCreated_at(userDetails.getCreated_at());
        user.setUpdated_at(userDetails.getUpdated_at());

        return userRepository.save(user);
    }

    public User createUser(User user) {
        String pw = passwordEncoder.encode(user.getPassword());

        user.setPassword(pw);

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        user.setCreated_at(sqlDate);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        return new User(user.get().getEmail(), user.get().getPassword());
    }

}
