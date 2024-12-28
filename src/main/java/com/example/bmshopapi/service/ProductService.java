package com.example.bmshopapi.service;

import com.example.bmshopapi.dto.CategoryDto;
import com.example.bmshopapi.entity.Category;
import com.example.bmshopapi.entity.Product;
import com.example.bmshopapi.repository.CategoryRepository;
import com.example.bmshopapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product saveProduct() throws IOException {
        return productRepository.save(readFileToList("E:/Job/shopping/resources/50via.txt"));
    }

    public List<CategoryDto> getAllProductsWithoutItems() {
        List<Category> categories = categoryRepository.findAll();
        List<Product> products = productRepository.findAllWithoutItems();

        // Tạo format mong muốn
        return categories.stream()
                .map(category -> {
                    List<Product> categoryProducts = products.stream()
                            .filter(product -> product.getCategoryId().equals(category.getId()))
                            .collect(Collectors.toList());
                    CategoryDto response = new CategoryDto();
                    response.setName(category.getName());
                    response.setProductList(categoryProducts);
                    return response;
                })
                .collect(Collectors.toList());
    }

    public Product readFileToList(String filePath) throws IOException {
        Product product = new Product();
        Path path = Paths.get(filePath);
        product.setItems(Files.readAllLines(path));

        product.setName("Via philipines 902 live ads");
        product.setDescription("✔ AE lưu ý via bị cp phone hàng ngày rất nhiều.\n" +
                "\n" +
                "✔ Backup tài nguyên vài nhiều via để an toàn.");
        product.setPrice(139000);
        product.setQuantity(product.getItems().size());
        return product;
    }
}
