package com.example.ecommerce.dao;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    List<CartDetail> findByCart(Cart cart);

    // findByIds
    @Query("SELECT cd FROM CartDetail cd WHERE cd.id IN :ids")
    List<CartDetail> findByIds(@Param("ids") List<Long> ids);

    CartDetail findByProductAndCart(Product product, Cart cart);

//    CartDetail findByProductIdAndCart(Long productId, Cart cart);

    CartDetail findByProductIdAndCartId(Long productId, Long cartId);


    boolean existsByProductAndCart(Product product, Cart cart);

//    boolean existsByProduct_IdAndCart(Long productId, Cart cart);
    // check existsByProduct_IdAndCart
    @Query("SELECT CASE WHEN COUNT(cd) > 0 THEN true ELSE false END FROM CartDetail cd WHERE cd.product.id = :productId AND cd.cart = :cart")
    boolean existsByProductIdAndCart(@Param("productId") Long productId, @Param("cart") Cart cart);

    //existsByProductIdAndCartId
    boolean existsByProductIdAndCartId(Long productId, Long cartId);

    @Query("SELECT SUM(cd.amount) FROM CartDetail cd WHERE cd.cart = :cart")
    int getTotalProductCountInCart(@Param("cart") Cart cart);

    // findByCartId
    @Query("SELECT cd FROM CartDetail cd WHERE cd.cart.id = :cartId")
    List<CartDetail> findByCartId(@Param("cartId") Long cartId);

    // deleteByProductIdAndCartId
    @Query("DELETE FROM CartDetail cd WHERE cd.product.id = :productId AND cd.cart.id = :cartId")
    void deleteByProductIdAndCartId(@Param("productId") Long productId, @Param("cartId") Long cartId);


    // caculate total price by list cartDetail id
    @Query("SELECT SUM(cd.price) FROM CartDetail cd WHERE cd.id IN :cartDetailIds")
    Double getTotalPriceByCartDetailIds(@Param("cartDetailIds") List<Long> cartDetailIds);

    // caculate total price by cart id
    @Query("SELECT SUM(cd.price) FROM CartDetail cd WHERE cd.cart.id = :cartId")
    Double getTotalPriceByCartId(@Param("cartId") Long cartId);

}
