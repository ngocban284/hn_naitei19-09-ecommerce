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
	
	List<Product> sortResultsByBuyCount(String query);
	
	List<Product> sortResultsByPrice(String query, String keyword);

	List<Product> sortResultsByBuyCount(Integer categoryId, String query);

	List<Product> sortResultsByPrice(Integer categoryId, String query, String keyword);

	List<Product> sortByBuyCount(Integer categoryId);

	List<Product> sortByPrice(Integer categoryId, String keyword);

	Product getReferenceById(Integer id);

	void softDeleteProduct(Long productId);

	void activeProduct(Long productId);

}
