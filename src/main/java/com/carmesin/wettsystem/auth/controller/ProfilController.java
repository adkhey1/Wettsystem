package com.carmesin.wettsystem.auth.controller;
import com.carmesin.wettsystem.auth.service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class ProfilController {

    @Autowired
    ProfilService profilService;

    @PostMapping("/booking")
    public String makeBooking(@RequestParam String contact, @RequestParam double amount, Model model,
                              HttpServletRequest request){
        model.addAttribute("profil", profilService.booking(contact, amount,
                getUuidFromCookie(request), model));
        return "profil";
    }


    @PostMapping("/credits")
    public String credits(Model model, HttpServletRequest request){
        model.addAttribute("profil", profilService.credits(getUuidFromCookie(request), model));
        return"profil";
    }

    private String getUuidFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("UUID"))
                    .map(Cookie::getValue)
                    .collect(Collectors.toList()).get(0);
        }
        return null;
    }

}
