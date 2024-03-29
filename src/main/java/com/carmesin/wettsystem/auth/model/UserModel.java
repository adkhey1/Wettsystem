package com.carmesin.wettsystem.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document("Users")
/**
 * Create the RegisterUser Object
 */
public class UserModel {

    private String name;
    private String password;
    private String title;
    private String country;
    private String postcode;
    private String street;
    private String streetnumber;
    private String email;
    private String IBAN;
    private String uuid;
    private double credits;
    private List<BetSlipModel> betSlips;

}

