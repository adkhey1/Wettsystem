package com.carmesin.wettsystem.bet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class BetSlipModel {

    private double moneySet;
    private double moneyGet;
    HashMap<String, Double> betSlip;
    //List<HashMap<String, Double>> betSlips;

}
