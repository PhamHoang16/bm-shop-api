package com.example.bmshopapi.repository;

import com.example.bmshopapi.entity.Category;
import com.example.bmshopapi.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    boolean existsByName(String name);

    List<Product> findByCategoryId(String categoryId);

    @Query(value = "{}", fields = "{ 'items': 0 }")
    List<Product> findAllWithoutItems();

    @Query(value = "{ 'name': ?0 }", fields = "{ 'items': 0 }")
    List<Category> findByNameWithoutItems(String name);

    @Query(value = "{ '_id': ?0 }", fields = "{ 'items': 0 }")
    Category findByIdWithoutItems(String id);
}
