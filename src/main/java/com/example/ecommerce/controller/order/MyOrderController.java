package com.example.ecommerce.controller.order;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.model.Status;
//import com.example.ecommerce.service.impl.OrderServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.controller.order.request.OrderRequest;
import org.springframework.http.ResponseEntity;
import com.example.ecommerce.model.CartDetail;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class MyOrderController {
        Long userID = 2L;  // Refactor later
        Long cancelStatusID = 5L;
        @Autowired
        private OrderService orderService;
//        @RequestMapping("/my-orders")
        public String index(Model map) {
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
        @PostMapping("/cancel-order/{orderCode}")
        public String cancelOrder(@PathVariable("orderCode") String orderCode, @RequestParam("reason") String reason, Model model) {
            Order order = orderService.findOrderByOrderCode(orderCode);
            orderService.updateOrderStatus(order.getId(),cancelStatusID,reason);
            return "redirect:/my-orders";
        }

        @PostMapping("/place")
        public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request) {
            try {
                User user = request.getUser();
                Order orderRequest = request.getOrder();
                Double total = request.getTotal();
                List<Long> cartDetailIds = request.getCartDetailIds();


//                String fullname = request.getFullname();
//                String phoneNumber = request.getPhoneNumber();
//                String address = request.getAddress();
//                String note = request.getNote();
//                Status status = request.getStatus();


//                 call orderService.placeOrder
//                OrderServiceImpl orderService = new OrderServiceImpl();
//                Order order = orderService.placeOrder(user, fullname, phoneNumber, address, note, status, cartDetailIds,total);
                Order order = orderService.placeOrder(user,orderRequest, cartDetailIds,total);

                return ResponseEntity.ok("Đặt hàng thành công");

            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Lỗi khi đặt hàng: " + e.getMessage());
            }
        }
}
