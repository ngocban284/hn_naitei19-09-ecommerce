package com.example.ecommerce.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsById(Long id);

    void deleteById(Long Id);

    Product save(Product product);
}