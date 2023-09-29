package com.example.ecommerce.controller.cart.request;

import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.Product;
// import user
import com.example.ecommerce.model.User;

public class RemoveFromCartRequest {
    private CartDetail cartDetail;

    private User user; // for test only

    private Long productId;

    // Getters and setters

    public CartDetail getCartDetail() {
        return cartDetail;
    }

    public void setCartDetail(CartDetail cartDetail) {
        this.cartDetail = cartDetail;
    }

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Long getProductId() {
        return productId;
    }
}
