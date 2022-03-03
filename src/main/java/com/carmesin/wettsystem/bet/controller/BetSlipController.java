package com.carmesin.wettsystem.bet.controller;

import com.carmesin.wettsystem.bet.service.BetSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BetSlipController {

    @Autowired
    BetSlipService betSlipService;

    @GetMapping("/wettschein")
    public void myBetSlip() {
        //all select bets from wette.html


    }

    @GetMapping("/alleWettscheine")
    public void allMyBetSlips() {

    }


    @GetMapping("/profil")
    public void profil() {

    }
}
