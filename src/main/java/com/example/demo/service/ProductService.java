package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    @Override
    public Iterable<Product> getProductList(){
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(String id){
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getProductsByColor(String color){
        return productRepository.findByColor(color);
    }

    @Override
    public List<Product> getProductsBySize(String size){
        return productRepository.findBySize(size);
    }
    
}