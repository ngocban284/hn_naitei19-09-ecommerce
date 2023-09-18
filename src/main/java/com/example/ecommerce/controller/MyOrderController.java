package com.example.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyOrderController {
        @RequestMapping("/myOrders")
        public String showMyOrders(Model map) {
//            map.addAttribute("orders" , orders);
            return "Users/MyOrders/myOrders";
        }
}
