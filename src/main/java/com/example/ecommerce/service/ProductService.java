package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import org.springframework.data.domain.Page;

public interface ProductService extends BaseService<Long, Product> {

    Page<Product> searchByName(String name, int page, int size, String sortField, String sortOrder);

    void softDeleteProduct(Long productId);

    void activeProduct(Long productId);
}
