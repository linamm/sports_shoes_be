package com.sportsshoes.controller;

import com.sportsshoes.bean.User;
import com.sportsshoes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/users")
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
    public String registerUser(@RequestBody User user) throws IOException {
        return service.registerUser(user);
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") String id, @RequestBody User user) throws IOException {
        User existingUser = service.getUserById(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        return service.updateUser(existingUser);
    }

    @DeleteMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        User existingUser = service.getUserById(id);
        service.deleteUser(existingUser);
        return "User deleted successfully";
    }
}