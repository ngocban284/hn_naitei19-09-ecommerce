package com.example.ecommerce.controller.cart.request;

import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Product;
// get user from session
import javax.servlet.http.HttpSession;

public class AddToCartRequest {
    private User user;
    private Product product;
    private int amount;

    private Integer productId;
    // Getters and setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    // get user from session
    public User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
