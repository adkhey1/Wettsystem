package com.carmesin.wettsystem.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    @GetMapping
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

//    @GetMapping("/wetten")
//    public String wetten() { return  "wetten"; }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}

