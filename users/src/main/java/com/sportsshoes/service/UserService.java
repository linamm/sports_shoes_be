package com.sportsshoes.service;

import com.sportsshoes.bean.User;
import com.sportsshoes.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser(User user) throws IOException {
        userRepository.save(user);
        return "User Registered Successfully with ID: " + user.getId();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String authenticateUser(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            return "Login Successful";
        }
        return "Invalid Credentials";
    }

    public User getUserById(String id) {
        return userRepository.findById(Long.parseLong(id)).orElse(null);
    }

    public String updateUser(User existingUser) {
        userRepository.save(existingUser);
        return "User updated successfully";
    }

    public void deleteUser(User existingUser) {
        userRepository.delete(existingUser);
    }
}
