package com.example.ecommerce.controller.admin;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String getOrderList(Model model) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("order", new Order());
        return "admin/orders/index";
    }

    @GetMapping("/{id}")
    public String getOrderDetail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("orderDetails", orderService.findOrderDetailsByOrderId(id));
        return "admin/orders/detail";
    }

    @PostMapping("/update-status")
    public String updateOrderStatus(@ModelAttribute("order") Order order,
                                    @RequestParam("orderId") Long id
    ) {

        orderService.updateOrderStatus(id, order.getStatus().getId(), order.getReason());
        return "redirect:/admin/orders/";
    }

}
