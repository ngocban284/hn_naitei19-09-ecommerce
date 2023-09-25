package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService extends BaseService<Long, Order> {
    List<OrderDetail> findOrderDetailsByOrderId(Long orderId);

    void updateOrderStatus(Long orderId, Long newStatusId, String reason);
    List<Order> findByUserId(Long userId);
    Order findOrderByOrderCode(String orderCode);

    Page<Order> findAllPaginated(int page, int size, String sortField, String sortOrder);

}
