package com.example.ecommerce.controller.admin;

import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String getOrderList(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/orders/index";
    }



}
