package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @PostMapping("/products")
    public String addProduct(@RequestBody Product product){
        productService.addProduct(product);
        return "Product added successfully";
    }

    @GetMapping("/products")
    public @ResponseBody Iterable<Product> getAllProducts(@RequestParam(required = false, defaultValue = "") String size, @RequestParam(required = false, defaultValue = "") String color){
        Iterable<Product> results = null;
        if(size.length()  > 1){
            results = productService.getProductsBySize(size);
        } else if(color.length() > 1){
            results = productService.getProductsByColor(color);
        } else {
            results = productService.getProductList();
        }
        return results;
    }

    @GetMapping("/products/{id}")
    public @ResponseBody Product getProduct(@PathVariable String id){
        return productService.getProduct(id);
    }    
}