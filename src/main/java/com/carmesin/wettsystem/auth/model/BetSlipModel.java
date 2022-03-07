package com.carmesin.wettsystem.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class BetSlipModel {

    private String russianHorse;
    private double russianQuote;
    private String germanHorse;
    private double germanQuote;
    private double creditsBet;
    private double totalQuote;
    private double totalWin;
    private String status;
}
