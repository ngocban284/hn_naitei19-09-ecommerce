package com.example.ecommerce.controller.user;

import com.example.ecommerce.dto.UserRegistrationDto;
import com.example.ecommerce.exeptions.RegistrationException;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto registrationDto(){
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(){
        return "user/auth/registration";
    }

    @PostMapping
    public String registration(@ModelAttribute("user")UserRegistrationDto registrationDto, Model model){
        try {
            if (userService.save(registrationDto) != null) {
                model.addAttribute("registrationSuccess", true);
            } else {
                model.addAttribute("registrationError", true);
            }
        } catch (RegistrationException e) {
            model.addAttribute("registrationError", true);
            model.addAttribute("error", e.getMessage());
        }
        return "user/auth/registration";
    }
}
