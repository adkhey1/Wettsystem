package com.carmesin.wettsystem.auth.controller;

import com.carmesin.wettsystem.auth.model.UserModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.carmesin.wettsystem.auth.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String password, @RequestParam String password2,
                           @RequestParam String title, @RequestParam String country, @RequestParam String postcode,
                           @RequestParam String street, @RequestParam String streetnumber, @RequestParam String email,
                           @RequestParam String IBAN, @RequestParam double credits, Model model,
                           HttpServletResponse response) {
        //return wert ist entweder "wetten" oder "register"

        String uuid = UUID.randomUUID().toString();
        UserModel userModel = new UserModel(name, password, title, country, postcode, street, streetnumber,
                email, IBAN, uuid, credits);
        return registerService.registrieren(userModel, password2, model, response);
    }

}
