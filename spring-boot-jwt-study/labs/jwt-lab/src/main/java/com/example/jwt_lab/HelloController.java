package com.example.jwt_lab;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
public class HelloController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public HelloController(JwtService jwtService, UserRepository userRepository){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password){
        User user = userRepository.findByUsername(username).orElse(null);
        
        if(user != null && encoder.matches(password, user.password)){
            return jwtService.create(username);
        }
        return "账号密码错误";
    }

}
