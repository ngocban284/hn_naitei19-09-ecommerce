package com.example.ecommerce.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dao.ProductRepository;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = Logger.getLogger(ProductRepository.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> searchByName(String name, int page, int size, String sortField, String sortOrder) {
        try {
            Sort sort = Sort.by(sortField);
            if ("desc".equalsIgnoreCase(sortOrder)) {
                sort = sort.descending();
            } else {
                sort = sort.ascending();
            }

            Pageable pageable = PageRequest.of(page, size, sort);

            if (name != null) {
                return productRepository.findByNameContaining(name, pageable);
            } else {
                return productRepository.findAll(pageable);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Page.empty(); // Return an empty page in case of an error
        }
    }

    @Override
    public void softDeleteProduct(Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setIsActive(false);
            productRepository.save(product);
        } else {
            logger.error("Product not found");
        }
    }

    @Override
    public void activeProduct(Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setIsActive(true);
            productRepository.save(product);
        } else {
            logger.error("Product not found");
        }
    }
    
	@Override
	public List<Product> getProducts() {
		return productRepository.getProducts();
	}

	@Override
	public List<Product> getProductsSortedByBuyCount() {
		return productRepository.getProductsSortedByBuyCount();
	}

	@Override
	public List<Product> getProductsByCategory(Integer categoryId) {
		return productRepository.getProductsByCategory(categoryId);
	}

	@Override
	public List<Product> searchAllCategories(String query) {
		return productRepository.searchAllCategories(query);
	}

	@Override
	public List<Product> searchByCategory(Integer categoryId, String query) {
		return productRepository.searchByCategory(categoryId, query);
	}

	@Override
	public Product getReferenceById(Integer id) {
		return productRepository.getReferenceById(id);
	}

	@Override
	public List<Product> sortByBuyCount(Integer categoryId) {
		return productRepository.sortByBuyCount(categoryId);
	}

	@Override
	public List<Product> sortResultsByBuyCount(Integer categoryId, String query) {
		return productRepository.sortResultsByBuyCount(categoryId, query);
	}


	@Override
	public List<Product> sortResultsByBuyCount(String query) {
		return productRepository.sortResultsByBuyCount(query);
	}

	@Override
	public List<Product> sortResultsByPrice(String query, String keyword) {
		return productRepository.sortResultsByPrice(query, keyword);
	}

	@Override
	public List<Product> sortResultsByPrice(Integer categoryId, String query, String keyword) {
		return productRepository.sortByPrice(categoryId, query);
	}

	@Override
	public List<Product> sortByPrice(Integer categoryId, String keyword) {
		return productRepository.sortByPrice(categoryId, keyword);
	}



}
