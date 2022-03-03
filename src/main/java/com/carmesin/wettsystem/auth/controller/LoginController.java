package com.carmesin.wettsystem.auth.controller;


import com.carmesin.wettsystem.auth.model.UserModel;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.carmesin.wettsystem.auth.model.UserModel;
import com.carmesin.wettsystem.auth.service.LoginService;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;


    @PostMapping("/login")
    public String login (@RequestParam String name, @RequestParam String password, Model model,
                         HttpServletResponse response) {
        //return wert ist entweder "login" oder "wetten"


        return loginService.login(name, password, model, response);
    }
}
