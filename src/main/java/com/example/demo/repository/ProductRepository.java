package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {
    List<Product> findByColor(String color);
    List<Product> findBySize(String size);
}