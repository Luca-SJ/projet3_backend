package com.projet3.demo.Repository;

import com.projet3.demo.Models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepo extends JpaRepository<Rental, Long> {

}
