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
	
	@Query(value = "SELECT * FROM products WHERE LOWER(name) LIKE CONCAT('%', LOWER(:query), '%') ORDER BY buy_count DESC", nativeQuery = true)
	List<Product> sortResultsByBuyCount(@Param("query") String query);
	
	@Query(value = "SELECT *\r\n"
			+ "FROM products\r\n"
			+ "WHERE LOWER(name) LIKE %:query% \r\n"
			+ "ORDER BY IF(:keyword = 'ASC', price, NULL) ASC,\r\n"
			+ "         IF(:keyword = 'DESC', price, NULL) DESC", nativeQuery = true)
	List<Product> sortResultsByPrice(@Param("query") String query, @Param("keyword") String keyword);

	@Query(value = "SELECT * FROM products WHERE category_id = :categoryId AND LOWER(name) LIKE %:query%", nativeQuery = true)
	List<Product> searchByCategory(@Param("categoryId") Integer categoryId, @Param("query") String query);
	
	@Query(value = "SELECT * FROM products WHERE category_id = :categoryId AND LOWER(name) LIKE %:query% ORDER BY buy_count DESC", nativeQuery = true)
	List<Product> sortResultsByBuyCount(@Param("categoryId") Integer categoryId, @Param("query") String query);
	
	@Query(value = " SELECT *\r\n"
			+ "FROM products\r\n"
			+ "WHERE category_id = :categoryId AND LOWER(name) LIKE %:query%\r\n"
			+ "ORDER BY IF(:keyword = 'ASC', price, NULL) ASC,\r\n"
			+ "         IF(:keyword = 'DESC', price, NULL) DESC", nativeQuery = true)
	List<Product> sortResultsByPrice(@Param("categoryId") Integer categoryId, @Param("query") String query, @Param("keyword") String keyword);
	
	@Query(value = "SELECT * FROM products  WHERE category_id = :categoryId  ORDER BY buy_count DESC", nativeQuery = true)
	List<Product> sortByBuyCount(@Param("categoryId") Integer categoryId);
	
	@Query(value = " SELECT *\r\n"
			+ "FROM products\r\n"
			+ "WHERE category_id = :categoryId\r\n"
			+ "ORDER BY IF(:keyword = 'ASC', price, NULL) ASC,\r\n"
			+ "         IF(:keyword = 'DESC', price, NULL) DESC", nativeQuery = true)
	List<Product> sortByPrice(@Param("categoryId") Integer categoryId, @Param("keyword") String keyword);
	
	
	public Optional<Product> findById(long id);


    boolean existsById(Long id);

    void deleteById(Long Id);

    Product save(Product product);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    Page<Product> findAll(Pageable pageable);
}
