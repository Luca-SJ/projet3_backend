package com.projet3.demo.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    private Date created_at;
    private Date updated_at;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String newName) {
        this.name = newName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_user"));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() { return email;    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date newCreated_at) {
        this.created_at = newCreated_at;
    }
    public Date getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(Date newUpdated_at) {
        this.updated_at = newUpdated_at;
    }


}
