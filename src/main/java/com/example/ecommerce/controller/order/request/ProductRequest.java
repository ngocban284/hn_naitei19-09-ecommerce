package com.example.ecommerce.controller.order.request;

public class ProductRequest {
    private Long id;
    private String name;
    private String category;
    private double price;
    private int stock;


    // Getters và setters
    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getCategory() {
        return category;
    }

    void setCategory(String category) {
        this.category = category;
    }

    double getPrice() {
        return price;
    }

    void setPrice(double price) {
        this.price = price;
    }

    int getStock() {
        return stock;
    }

    void setStock(int stock) {
        this.stock = stock;
    }

}
