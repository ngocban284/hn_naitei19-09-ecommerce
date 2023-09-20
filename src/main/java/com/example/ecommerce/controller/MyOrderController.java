package com.example.ecommerce.controller;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MyOrderController {
        Long userID = 2L;  // Refactor later
        @Autowired
        private OrderService orderService;
        @RequestMapping("/my-orders")
        public String showMyOrders(Model map) {
            List<Order> orders = orderService.findByUserId(userID);
            System.out.println(orders);
            map.addAttribute("orders" , orders);
            return "user/orders/index";
        }
}
