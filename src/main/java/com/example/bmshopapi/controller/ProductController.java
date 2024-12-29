package com.example.bmshopapi.controller;

import com.example.bmshopapi.dto.CategoryDto;
import com.example.bmshopapi.entity.Product;
import com.example.bmshopapi.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) throws IOException {
        productService.saveProduct(product);
        return ResponseEntity.ok("");
    }

    @GetMapping
    public ResponseEntity<?> getAllProductsWithoutItems() {
        List<CategoryDto> categories = productService.getAllProductsWithoutItems();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable String productId){
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @RequestBody Product product){
        return ResponseEntity.ok(productService.updateProduct(productId, product));
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategory(@RequestParam String name) {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/product")
    public ResponseEntity<?> getListProduct(@RequestParam String categoryName) {
        return ResponseEntity.ok(productService.searchProductByCategoryName(categoryName));
    }

    @PutMapping("/buy")
    public ResponseEntity<String> buy(@RequestParam String productId, @RequestParam String userId) {
        return ResponseEntity.ok(transactionService.buy(productId, userId));
    }

}
