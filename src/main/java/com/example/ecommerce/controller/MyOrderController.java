package com.example.ecommerce.controller;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MyOrderController {

        Long userID = 4L;  // Refactor later
        @Autowired
        private OrderService orderService;
        @RequestMapping("/my-orders")
        public String showMyOrders(Model map) {
            List<Order> orders = orderService.findByUserId(userID);
            map.addAttribute("orders" , orders);
            return "user/orders/index";
        }
        @GetMapping("/my-orders/{code}")
        public String getOrderDetail(Model model, @PathVariable("code") String code) {
            Order order = orderService.findOrderByOrderCode(code);
            model.addAttribute("orderDetails", order.getOrderDetails());
            model.addAttribute("orderTotal", order.getTotal());
            model.addAttribute("status", order.getStatus().getDescription().getStatus());
            return "user/orders/detail/index";
        }
}
