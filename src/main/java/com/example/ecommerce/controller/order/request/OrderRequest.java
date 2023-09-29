package com.example.ecommerce.controller.order.request;
import com.example.ecommerce.controller.order.request.ProductRequest;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Product;
import  com.example.ecommerce.model.Order;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.Status;

import java.util.List;

public class OrderRequest {
    private User user;

    private String fullname;
    private String phoneNumber;
    private String address;
    private String note;

    private Status status;

    private double total;

    // list productId and amount
    private List<ProductRequest> products;

    private List<CartDetail> cartDetail;

    // list cartDetailId
    private List<Long> cartDetailIds;

    private Order order;

    // Getters và setters
    public User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }

    public String getFullname() {
        return fullname;
    }

    void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    void setNote(String note) {
        this.note = note;
    }

    public double getTotal() {
        return total;
    }

    void setTotal(double total) {
        this.total = total;
    }

    List<ProductRequest> getProducts() {
        return products;
    }

    void setProducts(List<ProductRequest> products) {
        this.products = products;
    }


    public List<CartDetail> getCartDetail() {
        return cartDetail;
    }

    void setCartDetail(List<CartDetail> cartDetail) {
        this.cartDetail = cartDetail;
    }

    public Status getStatus() {
        return status;
    }

    void setStatus(Status status) {
        this.status = status;
    }

    public List<Long> getCartDetailIds() {
        return cartDetailIds;
    }

    void setCartDetailIds(List<Long> cartDetailIds) {
        this.cartDetailIds = cartDetailIds;
    }

    public Order getOrder() {
        return order;
    }

    void setOrder(Order order) {
        this.order = order;
    }
}
