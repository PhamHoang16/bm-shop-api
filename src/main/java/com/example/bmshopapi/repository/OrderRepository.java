package com.example.bmshopapi.repository;

import com.example.bmshopapi.entity.Order;
import com.example.bmshopapi.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);

    @Query(value = "{}", sort = "{ 'createdAt': -1 }")
    List<Order> findTop10ByOrderByCreatedAtDesc();
}
