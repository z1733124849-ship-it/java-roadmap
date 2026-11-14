package com.example.jwt_lab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component 
public class DataSeeder implements CommandLineRunner{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public DataSeeder(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public void run(String... args){
        if(userRepository.findByUsername("admin").isEmpty()){
            User u = new User();
            u.username = "admin";
            u.password = encoder.encode("admin123");
            userRepository.save(u);
        }
    }
    
}
