package com.example.bmshopapi.repository;

import com.example.bmshopapi.entity.Deposit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends MongoRepository<Deposit, String> {
    List<Deposit> findByUserId(String userId);
}
