package com.example.ecommerce.controller.admin;


import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/users")
public class UsersController {

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String listOrders(Model model,
                             @RequestParam(defaultValue = "") String fullname,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "7") int size) {
        Page<User> userPage;
        if (fullname.isEmpty()) {
            userPage = userService.findAllPaginated(page, size, "fullname", "asc");
        } else {
            userPage = userService.searchByFullname(fullname, page, size, "fullname", "asc");
        }

        model.addAttribute("usersPage", userPage);
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("fullname", fullname);

        return "admin/users/index";
    }


}
