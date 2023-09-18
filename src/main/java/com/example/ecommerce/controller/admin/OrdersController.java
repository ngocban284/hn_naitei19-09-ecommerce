package com.example.ecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/orders")
public class OrdersController {
    @GetMapping("/")
    public String getOrderList(Model model) {
        return "admin/orders/index";
    }
}
