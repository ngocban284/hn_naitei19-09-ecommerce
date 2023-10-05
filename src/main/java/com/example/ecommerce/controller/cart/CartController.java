package com.example.ecommerce.controller.cart;

import com.example.ecommerce.config.UserDetailsImpl;
import com.example.ecommerce.model.*;
import com.example.ecommerce.service.CartService;
import  com.example.ecommerce.service.CartDetailService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.controller.cart.request.AddToCartRequest;
import com.example.ecommerce.controller.cart.request.UpdateCartRequest;
import com.example.ecommerce.controller.cart.request.RemoveFromCartRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import java.util.List;
import com.example.ecommerce.dao.ProductRepository;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private ProductRepository productRepository;


    @RequestMapping("/my-cart")
    // get cart by user id
    public String showCartOfUser(Model map, HttpSession session) {
        UserDetailsImpl currentUser = (UserDetailsImpl) session.getAttribute("currentUser");

//        User user1 = userService.findById(currentUser.getId());
        Cart cart = cartService.findByUserId(currentUser.getId());

        List<CartDetail> cartDetails = cartDetailService.findByCartId(cart.getId());

        // get all product from cart Details
        for (CartDetail cartDetail : cartDetails) {
            Product product = productRepository.findById(cartDetail.getProduct().getId()).get();
            cartDetail.setProduct(product);
        }

        Double total = cartDetailService.getTotalPriceByCartId(cart.getId());

        map.addAttribute("total", total);
        map.addAttribute("cartDetails", cartDetails);
        return "cart/index";
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(@RequestBody AddToCartRequest request, HttpSession session) {
//        User user = request.getUserFromSession(session);
//        User user = request.getUser(); // for test only
        UserDetailsImpl currentUser = (UserDetailsImpl) session.getAttribute("currentUser");

        User user1 = userService.findById(currentUser.getId());

        Integer Id = request.getProductId();
        int amount = 1;


        Cart updatedCart = cartService.addItemToCartByProductId(user1, Id, amount);

        return ResponseEntity.ok(updatedCart);
    }



    @PutMapping("/update")
    public ResponseEntity<Cart> updateCartItem(@RequestBody UpdateCartRequest request) {
        CartDetail cartDetail = request.getCartDetail();
        int amount = request.getAmount();

        Cart updatedCart = cartService.updateCartItem(cartDetail, amount);

        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeItemFromCart(@RequestBody RemoveFromCartRequest request, HttpSession session) {
        UserDetailsImpl currentUser = (UserDetailsImpl) session.getAttribute("currentUser");

        User user1 = userService.findById(currentUser.getId());
//       User user = request.getUser();
       Integer Id = request.getProductId();

        cartService.removeProductInCartByProductId(user1, Id);

        return ResponseEntity.noContent().build();
    }
}
