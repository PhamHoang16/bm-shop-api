package com.example.bmshopapi.service;

import com.example.bmshopapi.dto.CategoryDto;
import com.example.bmshopapi.entity.Category;
import com.example.bmshopapi.entity.Order;
import com.example.bmshopapi.entity.Product;
import com.example.bmshopapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Product saveProduct(Product product) throws IOException {
        return productRepository.save(product);
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> searchProductByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        return productRepository.findByCategoryId(category.getId());
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

    public void readCategory(String filePath) throws IOException {
        Product product = new Product();
        Path path = Paths.get("E:/Job/shopping/resources/category_list.txt");
        List<String> categoryStr = (Files.readAllLines(path));

        List<Category> categories = categoryStr.stream()
                .map(category -> {
                    Category category1 = new Category();
                    category1.setName(category);
                    return category1;
                })
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);
    }

    public Product updateProduct(String productId, Product newProduct) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        Product updatedProduct = Product.builder()
                .id(productId)
                .name(newProduct.getName())
                .categoryName(newProduct.getCategoryName())
                .description(newProduct.getDescription())
                .price(newProduct.getPrice())
                .quantity(newProduct.getQuantity())
                .items(newProduct.getItems())
                .categoryId(newProduct.getCategoryId())
                .build();
        return productRepository.save(updatedProduct);
    }

    @Transactional
    public List<String> buy(String userId, String productId, int number) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<String> items = new ArrayList<>();

        userRepository.findById(userId).ifPresent(user -> {
            double totalPrice = product.getPrice() * number;
            if (user.getBalance() >= totalPrice) {
                user.setBalance(user.getBalance() - totalPrice);
                userRepository.save(user);

                for (int i = 0; i < number; i++) {
                    if (product.getItems().isEmpty()) {
                        throw new RuntimeException("Not enough items in stock");
                    }
                    items.add(product.getItems().remove(0));
                }
                product.setQuantity(product.getQuantity() - number);
                productRepository.save(product);
                Order order = Order.builder().productName(product.getName()).productId(productId).userId(userId).totalPrice(totalPrice).items(items).build();
                orderRepository.save(order);
            } else {
                throw new RuntimeException("Insufficient balance");
            }
        });

        return items;
    }
}
