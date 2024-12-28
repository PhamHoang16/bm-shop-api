package com.example.bmshopapi.service;

import com.example.bmshopapi.entity.Product;
import com.example.bmshopapi.entity.Transaction;
import com.example.bmshopapi.repository.ProductRepository;
import com.example.bmshopapi.repository.TransactionRepository;
import com.example.bmshopapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public void deposit(String userId, double amount) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setBalance(user.getBalance() + amount);
            userRepository.save(user);
            transactionRepository.save(new Transaction(null, userId, amount, "success", "deposit", null));
        });
    }

    @Transactional
    public String buy(String userId, String productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        userRepository.findById(userId).ifPresent(user -> {
            if (user.getBalance() >= product.getPrice()) {
                user.setBalance(user.getBalance() - product.getPrice());
                userRepository.save(user);
                transactionRepository.save(new Transaction(null, userId, product.getPrice(), "success", "buy", null));
            } else {
                transactionRepository.save(new Transaction(null, userId, product.getPrice(), "failed", "buy", null));
            }
        });
            product.setQuantity(product.getQuantity() - 1);
            String item = product.getItems().get(0);
            product.getItems().remove(0);
            productRepository.save(product);
        return item;
    }
}
