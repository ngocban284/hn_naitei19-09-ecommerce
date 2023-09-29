package com.example.ecommerce.controller.order.request;
import com.example.ecommerce.controller.order.request.ProductRequest;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.CartDetail;

import java.util.List;

public class OrderDetailRequest {
    private User user;

    private String fullname;
    private String phoneNumber;
    private String address;
    private String note;

   // list productId and amount
    private List<ProductRequest> products;

    private CartDetail cartDetail;


    // Getters và setters
    User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }

    String getFullname() {
        return fullname;
    }

    void setFullname(String fullname) {
        this.fullname = fullname;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    String getNote() {
        return note;
    }

    void setNote(String note) {
        this.note = note;
    }

    List<ProductRequest> getProducts() {
        return products;
    }

    void setProducts(List<ProductRequest> products) {
        this.products = products;
    }

    CartDetail getCartDetail() {
        return cartDetail;
    }

    void setCartDetail(CartDetail cartDetail) {
        this.cartDetail = cartDetail;
    }

}
