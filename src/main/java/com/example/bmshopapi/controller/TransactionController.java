package com.example.bmshopapi.controller;

import com.example.bmshopapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PutMapping("/buy")
    public ResponseEntity<String> buy(@RequestParam String productId, @RequestParam String userId) {
        return ResponseEntity.ok(transactionService.buy(productId, userId));
    }
}
