package com.projet3.demo.Repository;

import com.projet3.demo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    public Optional<User> findByEmailAndPassword(String email, String password);
}
