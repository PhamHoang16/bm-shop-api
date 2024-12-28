package com.example.bmshopapi.controller;

import com.example.bmshopapi.dto.CategoryDto;
import com.example.bmshopapi.entity.Category;
import com.example.bmshopapi.entity.Product;
import com.example.bmshopapi.service.ProductService;
import com.example.bmshopapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) throws IOException {
        productService.saveProduct();
        return ResponseEntity.ok("");
    }

    @GetMapping
    public ResponseEntity<?> getAllProductsWithoutItems() {
        List<CategoryDto> categories = productService.getAllProductsWithoutItems();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/buy")
    public ResponseEntity<String> buy(@RequestParam String productId, @RequestParam String userId) {
        return ResponseEntity.ok(transactionService.buy(productId, userId));
    }

}
