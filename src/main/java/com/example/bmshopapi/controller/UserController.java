package com.example.bmshopapi.controller;

import com.example.bmshopapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{userId}")
    public ResponseEntity<?> getUser(@RequestParam String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam String userId, @RequestParam double amount) {
        userService.deposit(userId, amount);
        return ResponseEntity.ok("Deposit successful");
    }
}
