package com.webshop.admin.controller;

import lnu.webshop.library.dto.AdminDto;
import lnu.webshop.library.model.Admin;
import lnu.webshop.library.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AdminServiceImpl adminService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("title","Login");
        return "login";
    }

    @RequestMapping({"/index","/"})
    public String home(Model model){
        model.addAttribute("title","Home page");
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("title","Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }
    @GetMapping("/forgot-password")
    public String forgotPasswordForm(Model model){
        model.addAttribute("title","Reset password");
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto")AdminDto adminDto,
                              BindingResult result,
                              Model model
    ) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("adminDto",adminDto);
                return "register";
            }
            String username = adminDto.getUserName();
            Admin admin = adminService.findByUsername(username);
            if (admin!=null) {
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("emailError","This email is already taken!");

                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("success","Registration successfully");

            } else {
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("passwordError","Passwords isn`t equals!");

                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("serverError","Something went wrong.Try again later");

        }
        return "register";
    }
}
