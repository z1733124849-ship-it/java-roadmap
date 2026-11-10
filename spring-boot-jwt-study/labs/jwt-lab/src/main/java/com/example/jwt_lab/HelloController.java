package com.example.jwt_lab;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password){
        if("admin".equals(username) && "admin123".equals(password)){
            return UUID.randomUUID().toString();
        }
        return "账号密码错误";
    }

}
