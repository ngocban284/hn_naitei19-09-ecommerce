package com.example.ecommerce.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query(value = "select * from products", nativeQuery = true)
	public List<Product> getProducts();

	@Query(value = "select * from products where id =?1", nativeQuery = true)
	public Product getReferenceById(Integer id);

	@Query(value = "SELECT * FROM products ORDER BY buy_count DESC LIMIT 16", nativeQuery = true)
	public List<Product> getProductsSortedByBuyCount();

	@Query(value = "SELECT p.* FROM products p JOIN categories c ON p.category_id = c.id WHERE p.category_id = ?1", nativeQuery = true)
	public List<Product> getProductsByCategory(Integer category_id);

	@Query(value = "SELECT * FROM products WHERE LOWER(name) LIKE %:query%", nativeQuery = true)
	List<Product> searchAllCategories(@Param("query") String query);

	@Query(value = "SELECT * FROM products WHERE category_id = :categoryId AND LOWER(name) LIKE %:query%", nativeQuery = true)
	List<Product> searchByCategory(@Param("categoryId") Integer categoryId, @Param("query") String query);
	
	public Optional<Product> findById(long id);


    boolean existsById(Long id);

    void deleteById(Long Id);

    Product save(Product product);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    Page<Product> findAll(Pageable pageable);
}
