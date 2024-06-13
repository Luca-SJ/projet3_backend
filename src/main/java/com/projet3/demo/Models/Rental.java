package com.projet3.demo.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;

import java.time.Instant;
import java.util.Date;

@Data
@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private float surface;
    private float price;
    private String picture;
    private String description;
    private Long owner_id;
    private Date created_at = new Date();
    private Instant updated_at;


    // GET-SET | ID
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    // GET-SET | NAME
    public String getName() {
        return name;
    }
    public void setName(String newName) {
        this.name = newName;
    }

    // GET-SET | SURFACE
    public float getSurface() {
        return surface;
    }
    public void setSurface(float newSurface) {
        this.surface = newSurface;
    }

    // GET-SET | PRIX
    public float getPrice() {
        return price;
    }
    public void setPrice(float newPrice) {
        this.price = newPrice;
    }

    // GET-SET | PICTURE
    public String getPicture() {
        return picture;
    }
    public void setPicture(MultipartFile newPicture) throws IOException {
        if (newPicture == null) {
            return;
        }
        String removeSpaceNamePicture = newPicture.getOriginalFilename().replaceAll("\\s", "_");
        Path uploadFolder = Paths.get("src/main/resources/static/images/" + removeSpaceNamePicture);

        // METHODE 1
//        newPicture.transferTo(uploadFolder);
//        this.picture = "/images/" + removeSpaceNamePicture;

        // METHODE 2
        try (OutputStream os = Files.newOutputStream(uploadFolder)) {
            os.write(newPicture.getBytes());
            this.picture = "/images/" + removeSpaceNamePicture;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GET-SET | DESCRIPTION
    public String getDescription() {
        return description;
    }
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    // GET-SET | OWNER_ID
    public Long getOwner_id() {
        return owner_id;
    }
    public void setOwner_id(Long newOwner_id) {
        this.owner_id = newOwner_id;
    }

    // GET-SET | CREATED_AT
    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date newCreated_at) {
        this.created_at = newCreated_at;
    }

    // GET-SET | UPDATED_AT
    public Instant getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(Instant newUpdated_at) {
        this.updated_at = newUpdated_at;
    }
}
