package com.example.ecommerce.service.impl;

import com.example.ecommerce.dao.OrderDetailRepository;
import com.example.ecommerce.dao.OrderRepository;
import com.example.ecommerce.dao.StatusRepository;
import com.example.ecommerce.model.*;
import com.example.ecommerce.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private StatusRepository statusRepository;

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
    public List<Order> findByUserId(Long userId) {
        try {
            return orderRepository.findByUserId(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<OrderDetail> findOrderDetailsByOrderId(Long orderId) {

        return orderDetailRepository.findByOrderId(orderId);
    }


    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, Long newStatusId, String reason) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null) {
            // Retrieve the new Status from the database based on the newStatusId
            Status newStatus = statusRepository.findById(newStatusId).orElse(null);
            if (newStatus != null) {
                // Check if the status is changing from REJECTED to another status
               if(newStatus.getDescription() == OrderStatus.REJECTED){
                   if(reason == null || reason.isEmpty()){
                       order.setReason("No reason");
                   }
                     else{
                          order.setReason(reason);
                     }
               }
                // Update the order's status with the new Status object
                order.setStatus(newStatus);
                // Save the updated order
                orderRepository.save(order);
            }
        }

    }


}
