package com.example.ecommerce.dao;

import com.example.ecommerce.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("SELECT od FROM OrderDetail od WHERE od.order.id = :orderId")
    public List<OrderDetail> findByOrderId(Long orderId);

//    // placeOrderDetail
//    OrderDetail placeOrderDetail(OrderDetail orderDetail);
//
//    // update OrderDetail
//    OrderDetail updateOrderDetail(OrderDetail orderDetail);

}
