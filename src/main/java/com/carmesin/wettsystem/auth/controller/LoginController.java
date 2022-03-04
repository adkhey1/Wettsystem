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

    /**
     *
     * @param name input of name
     * @param password input of password
     * @param model
     * @param response add cookie to response
     * @return login by incorrect entries (repeated login.html) / redirekt:wetten goes to wetten.html
     */
    @PostMapping("/login")
    public String login (@RequestParam String name, @RequestParam String password, Model model,
                         HttpServletResponse response) {

        //Returns login or wetten
        return loginService.login(name, password, model, response);
    }
}
