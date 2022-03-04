package com.carmesin.wettsystem.bet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("Horses")
/**
 * Create the HorseModel Object
 */
public class HorseModel {

    private String name;
    private String origin;
    private String rider;
    private int speed;
    private int starts;
    private int balance;
    private int endurance;
    private int reaction;
    private Leagues league;

}
