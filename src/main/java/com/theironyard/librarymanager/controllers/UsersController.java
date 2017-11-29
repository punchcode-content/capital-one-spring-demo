package com.theironyard.librarymanager.controllers;

import com.theironyard.librarymanager.entities.User;
import com.theironyard.librarymanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UsersController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "users/register";
    }

    @PostMapping("/register")
    public String handleRegistration(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "users/register";
        }

        userService.save(user);
        return "redirect:/";
    }

}
