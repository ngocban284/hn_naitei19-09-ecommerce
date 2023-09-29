package com.example.ecommerce.controller.admin;


import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(Model model,
                             @RequestParam(defaultValue = "") String name,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "7") int size) {
        Page<Product> productsPage;

        productsPage = productService.searchByName(name, page, size, "name", "asc");

        model.addAttribute("productsPage", productsPage);
        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("name", name);

        return "admin/products/index";
    }

    @PostMapping("/soft-delete/{productId}")
    public ResponseEntity<String> softDeleteProduct(@PathVariable Long productId) {
        try {
            productService.softDeleteProduct(productId);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not delete product.");
        }
    }

    @PostMapping("/active/{productId}")
    public ResponseEntity<String> activeProduct(@PathVariable Long productId) {
        try {
            productService.activeProduct(productId);
            return ResponseEntity.ok("Product active successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not active product.");
        }
    }
}
