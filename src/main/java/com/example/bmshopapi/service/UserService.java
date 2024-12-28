package com.example.bmshopapi.service;

import com.example.bmshopapi.entity.User;
import com.example.bmshopapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void deposit(String userId, double amount) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setBalance(user.getBalance() + amount);
            userRepository.save(user);
        });
    }
}
