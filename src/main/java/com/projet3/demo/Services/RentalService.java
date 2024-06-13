package com.projet3.demo.Services;

import com.projet3.demo.Exceptions.ResourceNotFoundException;
import com.projet3.demo.Models.Rental;
import com.projet3.demo.Models.RentalDTO;
import com.projet3.demo.Repository.RentalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepo rentalRepository;

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Rental findByID(Long RentalID) throws ResourceNotFoundException {
        return rentalRepository.findById(RentalID)
        .orElseThrow(()->new ResourceNotFoundException("rental avec ID : " + RentalID + " est inexistant"));
    }

    public void deleteByID(Long RentalID) throws ResourceNotFoundException {
        Rental rent = rentalRepository.findById(RentalID)
                .orElseThrow(()->new ResourceNotFoundException("rental avec ID : " + RentalID + " est inexistant"));
        rentalRepository.delete(rent);
    }

    public Rental updateRentalByID(Long RentalID, RentalDTO rentDetails) throws ResourceNotFoundException, IOException {
        Rental rent = rentalRepository.findById(RentalID)
                .orElseThrow(()->new ResourceNotFoundException("rental avec ID : "+ RentalID + " est inexistant"));

        rent.setUpdated_at(Instant.now());
        rent.setName(rentDetails.getName());
        rent.setSurface(rentDetails.getSurface());
        rent.setPrice(rentDetails.getPrice());
        rent.setDescription(rentDetails.getDescription());

        return rentalRepository.save(rent);
    }

    public Rental createRental(RentalDTO rent) throws IOException {
        return rentalRepository.save(rent.toEntity());
    }

}
