package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    private String id;
    private String sku;
    private String name;
    private String description;
    private String size;
    private String color;

    public Product(String id, String sku, String name, String description, String size, String color) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.size = size;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

}