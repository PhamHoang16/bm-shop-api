package com.example.bmshopapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "deposits")
public class Deposit {
    private String id;
    private String userId;
    private String username;
    private double amount;
    private String detail;
    private LocalDateTime createdAt;
}
