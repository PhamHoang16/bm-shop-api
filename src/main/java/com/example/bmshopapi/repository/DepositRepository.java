package com.example.bmshopapi.repository;

import com.example.bmshopapi.entity.Deposit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends MongoRepository<Deposit, String> {
    List<Deposit> findByUserId(String userId);

    @Query(value = "{}", sort = "{ 'createdAt': -1 }")
    List<Deposit> findTop10ByOrderByCreatedAtDesc();
}
