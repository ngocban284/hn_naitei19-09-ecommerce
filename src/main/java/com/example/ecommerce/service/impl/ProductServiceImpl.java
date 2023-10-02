package com.example.ecommerce.service.impl;

import com.example.ecommerce.dao.ProductRepository;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.RoleName;
import com.example.ecommerce.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = Logger.getLogger(ProductRepository.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

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
    public void softDeleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setIsActive(false);
            productRepository.save(product);
        } else {
            logger.error("Product not found");
        }
    }

    @Override
    public void activeProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setIsActive(true);
            productRepository.save(product);
        } else {
            logger.error("Product not found");
        }
    }
}
