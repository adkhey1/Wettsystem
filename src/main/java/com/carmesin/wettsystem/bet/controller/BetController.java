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

import java.util.HashMap;
import java.util.List;

@Controller
public class BetController {

    @Autowired
    BetService betService;

    @GetMapping("/wetten")
    public String loadQuotes(Model model) {
        HashMap<String, List<HorseWithQuote>> response = betService.calculateQuotes();
        model.addAttribute("germanLeague", response.get("germanLeague"));
        model.addAttribute("russianLeague", response.get("russianLeague"));
        return "wetten";
    }

    @GetMapping("/champions")
    public String loadQuotesChampions(Model model) {

        model.addAttribute("champions", betService.calculateQuotesChampions(model));
        return "wetten";
    }

    @PostMapping("/placeBet")
    public String placeBet(@RequestParam String input_russian_bet_name, @RequestParam String input_german_bet_name, Model model) {
        String status = betService.placeBet(input_german_bet_name, input_russian_bet_name);
        model.addAttribute("end", status);
        return "wetten";
    }

}

