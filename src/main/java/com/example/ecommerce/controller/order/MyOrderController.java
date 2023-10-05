package com.example.ecommerce.controller.order;

import com.example.ecommerce.config.UserDetailsImpl;
import com.example.ecommerce.model.*;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.CartDetailService;
import com.example.ecommerce.service.UserService;
//import com.example.ecommerce.dao.CartDetailRepository;
//import com.example.ecommerce.dao.ProductRepository;
//import com.example.ecommerce.service.impl.OrderServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.controller.order.request.OrderRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class MyOrderController {
        Long cancelStatusID = 5L;
        @Autowired
        private OrderService orderService;

        @Autowired
        private CartService cartService;

        @Autowired
        private UserService userService;

        @Autowired
        private CartDetailService cartDetailService;

//        @Autowired
//        private CartDetailRepository cartDetailRepository;
//
//        @Autowired
//        private ProductRepository productRepository;

        @RequestMapping("/my-orders")
        public String index(Model map, HttpSession session) {
            UserDetailsImpl currentUser = (UserDetailsImpl) session.getAttribute("currentUser");
            List<Order> orders = orderService.findByUserId(currentUser.getId());
            map.addAttribute("orders" , orders);
            map.addAttribute("user", currentUser);
            return "user/orders/index";
        }

//        @RequestMapping("/place-orders")
//        public String placeOrder(Model map,@RequestParam("cartDetailIds") Long[] cartDetailIds,
//                                 @RequestParam("total") double total) {
//            List<Order> orders = orderService.findByUserId(userID);
//
//            System.out.println("cartDetailIds: " + cartDetailIds);
//            System.out.println("total: " + total);
//
//            map.addAttribute("orders" , orders);
//
//            return "user/orders/place/index";
//        }
        @RequestMapping("/place-orders")
        public String placeOrder(Model map,
                                 @RequestParam("cartDetailIds") String cartDetailIds,
                                 @RequestParam("total") double total,HttpSession session) {
            UserDetailsImpl currentUser = (UserDetailsImpl) session.getAttribute("currentUser");

//            User user1 = userService.findById(currentUser.getId());
            Cart cart = cartService.findByUserId(currentUser.getId());
            List<CartDetail> cartDetails = cartDetailService.findByCartId(cart.getId());

            // use assosiation to get product from cartDetail
            for (CartDetail cartDetail : cartDetails) {
                Product product = cartDetail.getProduct();
                cartDetail.setProduct(product);
            }

            // cartDetailIds look like "123"
            // remove " and get list of cartDetailIds
            cartDetailIds = cartDetailIds.replace("\"", "");
            // now cartDetailIds look like 123. get 1,2,3 to list
            List<Long> cartDetailIdsList = new ArrayList<>();
            for (int i = 0; i < cartDetailIds.length(); i++) {
                cartDetailIdsList.add(Long.parseLong(String.valueOf(cartDetailIds.charAt(i))));
            }


//            map.addAttribute("orders", orders);
            map.addAttribute("cartDetailIds", cartDetailIdsList);
            map.addAttribute("cartDetails", cartDetails);
            map.addAttribute("total", total);
            return "user/orders/place/index";
        }


    @GetMapping("/my-orders/{code}")
        public String getOrderDetail(Model model, @PathVariable("code") String code, HttpSession session) {
            UserDetailsImpl currentUser = (UserDetailsImpl) session.getAttribute("currentUser");
            Order order = orderService.findOrderByOrderCode(code);
            System.out.println("order: " + order);
            model.addAttribute("orderDetails", order.getOrderDetails());
            model.addAttribute("user", currentUser);
            model.addAttribute("orderTotal", order.getTotal());
            model.addAttribute("status", order.getStatus().getDescription().getStatus());
            return "user/orders/detail/index";
        }
        @PostMapping("/cancel-order/{orderCode}")
        public String cancelOrder(@PathVariable("orderCode") String orderCode, @RequestParam("reason") String reason, Model model) {
            System.out.println("orderCode: " + orderCode);
            Order order = orderService.findOrderByOrderCode(orderCode);
            System.out.println("order: " + order);
            orderService.updateOrderStatus(order.getId(),cancelStatusID,reason);
            System.out.println("reason: " + reason);
            return "redirect:/my-orders";
        }

        @PostMapping("/place")
        public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request, HttpSession session) {
            try {
//                User user = request.getUser();
                UserDetailsImpl currentUser = (UserDetailsImpl) session.getAttribute("currentUser");
                 User user1 = userService.findById(currentUser.getId());

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
                Order order = orderService.placeOrder(user1,orderRequest, cartDetailIds,total);

                return ResponseEntity.ok("Đặt hàng thành công");

            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Lỗi khi đặt hàng: " + e.getMessage());
            }
        }
}
