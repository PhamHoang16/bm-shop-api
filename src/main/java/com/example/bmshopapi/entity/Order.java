package com.example.bmshopapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class Order {
    private String id;
    private String userId;
    private String categoryName;
    private double price;
    private String status;
    private String createdAt;
}
