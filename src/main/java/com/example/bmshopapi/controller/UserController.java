package com.example.bmshopapi.controller;

import com.example.bmshopapi.entity.User;
import com.example.bmshopapi.repository.UserRepository;
import com.example.bmshopapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam String userId, @RequestParam double amount) {
        userService.deposit(userId, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

}
