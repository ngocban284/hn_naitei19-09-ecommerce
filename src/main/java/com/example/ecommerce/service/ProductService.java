package com.example.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Product;

@Service
public interface ProductService {
	Page<Product> searchByName(String name, int page, int size, String sortField, String sortOrder);

	List<Product> getProducts();

	List<Product> getProductsSortedByBuyCount();

	List<Product> getProductsByCategory(Integer categoryId);

	List<Product> searchAllCategories(String query);

	List<Product> searchByCategory(Integer categoryId, String query);

	Product getReferenceById(Integer id);

    void softDeleteProduct(Long productId);

    void activeProduct(Long productId);

}
