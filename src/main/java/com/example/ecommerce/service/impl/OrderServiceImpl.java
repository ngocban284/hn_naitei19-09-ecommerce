package com.example.ecommerce.service.impl;

import com.example.ecommerce.dao.OrderDetailRepository;
import com.example.ecommerce.dao.OrderRepository;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderDetail;
import com.example.ecommerce.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<Order> findAll() {
        try {
            return orderRepository.findAll();

        } catch (Exception e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Order findById(Long id) {
        try {
            return orderRepository.findById(id).get();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<OrderDetail> findOrderDetailsByOrderId(Long orderId) {

        return orderDetailRepository.findByOrderId(orderId);
    }
}
