package com.example.ecommerce.dao;
// cart repository
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart createCartByUser(User user);

    Cart findByUser(User user);

//    Cart findByUserId(Long userID);
    // findByUserId
    @Query(value = "select * from carts where user_id = ?1", nativeQuery = true)
    Cart findByUserId(Long userID);

    boolean existsByUser(User user);

    void deleteByUser(User user);

    Cart save(Cart cart);


    // find all cart detail by cart id
    @Query(value = "select * from cart_detail where cart_id = ?1", nativeQuery = true)
    List<CartDetail> findAllCartDetailByCartId(Long cartId);
}