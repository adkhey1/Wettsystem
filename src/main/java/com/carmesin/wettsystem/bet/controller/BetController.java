package com.carmesin.wettsystem.bet.controller;

import com.carmesin.wettsystem.bet.service.BetService;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BetController {

    @Autowired
    BetService betService;

    @GetMapping("/wetten")
    public String loadQuotes(Model model) {
        return betService.calculateQuotes(model);
    }

    @GetMapping("/champions")
    public String loadQuotesChampions(Model model) {

        model.addAttribute("champions", betService.calculateQuotesChampions(model));
        return "wetten";
    }

    @GetMapping("/betSlip")
    public String loadBetSlip(Model model){
        //wettschein muss in wettschein.html gezeigt werden
        //alle Quoten die man gewählt hat, den Einsatz, den man auswählen kann
        //und den möglichen gewinn
        return "wettschein";
    }

}

