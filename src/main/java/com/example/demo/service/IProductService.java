package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Product;

public interface IProductService {
    Product addProduct(Product product);
    Iterable<Product> getProductList();
    Product getProduct(String id);
    List<Product> getProductsByColor(String color);
    List<Product> getProductsBySize(String size);
}