package com.carmesin.wettsystem.bet.controller;

import com.carmesin.wettsystem.bet.model.HorseWithQuote;
import com.carmesin.wettsystem.bet.service.BetService;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BetController {

    @Autowired
    BetService betService;

    @GetMapping("/wetten")
    public String loadQuotes(Model model) {
        //Fill all horses into the hashmap response
        HashMap<String, List<HorseWithQuote>> response = betService.calculateQuotes();

        //get german Horses by HashMap Key "germanLeague"
        model.addAttribute("germanLeague", response.get("germanLeague"));
        //get russian Horses by HashMap Key "russianLeague"
        model.addAttribute("russianLeague", response.get("russianLeague"));
        return "wetten";
    }

    //not in use
    @GetMapping("/champions")
    public String loadQuotesChampions(Model model) {

        model.addAttribute("champions", betService.calculateQuotesChampions(model));
        return "wetten";
    }

    @PostMapping("/placeBet")
    public String placeBet(@RequestParam String input_russian_bet_name, @RequestParam String input_german_bet_name,
                           @RequestParam double input_credit, Model model, HttpServletRequest request) {
        String uuid = getUuidFromCookie(request);
        String status = betService.placeBet(input_german_bet_name, input_russian_bet_name, input_credit, uuid);
        model.addAttribute("end", status);
        return "wetten";
    }

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

