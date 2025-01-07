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

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(userService.signIn(email, password));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        return ResponseEntity.ok(userService.signUp(user));
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam String username, @RequestParam double amount) {
        userService.deposit(username, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @GetMapping("/deposit")
    public ResponseEntity<?> getDepositHistory(@RequestParam String userId) {
        return ResponseEntity.ok(userService.getDepositHistory(userId));
    }

    @GetMapping("/deposit/all")
    public ResponseEntity<?> getAllDepositHistory() {
        return ResponseEntity.ok(userService.getAllDepositHistory());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/deposit/last-deposit")
    public ResponseEntity<?> getLast10Deposit() {
        return ResponseEntity.ok(userService.getLast10Deposit());
    }
}
