package com.example.bmshopapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transactions")
public class Transaction {
    private String id;
    private String userId;
    private double amount;
    private String status;
    private String detail;
    private String createdAt;
}
