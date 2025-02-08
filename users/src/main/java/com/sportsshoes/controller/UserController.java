package com.sportsshoes.controller;

import com.sportsshoes.bean.User;
import com.sportsshoes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/user")
    public String getUserByName(@RequestParam String name) {
        User foundUser = service.getUserByUsername(name);
        if (foundUser != null) {
            return "Found User with name: " + name;
        } else {
            return "User not found";
        }
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestBody User user) {
        return service.authenticateUser(user);
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        RestTemplate template = new RestTemplate();
        String url = "http://localhost:8082/register";
        return template.postForObject(url, user, String.class);
    }
}