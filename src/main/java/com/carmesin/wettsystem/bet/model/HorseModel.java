package com.carmesin.wettsystem.bet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("Horses")
public class HorseModel {

    //Pferdenamen Alkohol und Modemarken
    //Vodi, Hennesy, Barcardi, Sky, Goose, Belvedere, Beluga, Crystal, Jacky, Malibu -> Russische Liga
    //Prada, Polo, Chanel, Valentino!!!, Bottega, Givenchy, Gucci, Fendi, Vuitton, McQueen -> Deutsche Liga

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
