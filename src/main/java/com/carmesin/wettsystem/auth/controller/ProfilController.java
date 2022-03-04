package com.carmesin.wettsystem.auth.controller;
import com.carmesin.wettsystem.auth.service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        //Value of model is the return String from the methode
        model.addAttribute("profil", profilService.booking(contact, amount,
                getUuidFromCookie(request), model));
        return "profil";
    }


    @PostMapping("/credits")
    public String credits(Model model, HttpServletRequest request){
        //Value of model is the return String from the methode
        model.addAttribute("profil", profilService.credits(getUuidFromCookie(request), model));
        return"profil";
    }

    /**
     * Get UUID
     *
     * @param request
     * @return value from Cookie with getName("UUDI") / return null if cookie not exist (never)
     */
    private String getUuidFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            //goes through all cookies until it finds one with the name UUID and returns it
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("UUID"))
                    .map(Cookie::getValue)
                    .collect(Collectors.toList()).get(0);
        }
        return null;
    }

}
