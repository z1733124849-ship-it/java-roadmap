package com.example.jwt_lab;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "users")
public class User {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long id;

    public String username;

    public String password;

}
