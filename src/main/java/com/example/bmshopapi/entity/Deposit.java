package com.example.bmshopapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "deposits")
public class Deposit {
    private String id;
    private String userId;
    private double amount;
    private String detail;

    @CreatedDate
    private String createdAt;
}
