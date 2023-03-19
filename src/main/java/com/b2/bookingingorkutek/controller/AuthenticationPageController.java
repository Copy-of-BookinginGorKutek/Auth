package com.b2.bookingingorkutek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/auth-page")
public class AuthenticationPageController {
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

}
