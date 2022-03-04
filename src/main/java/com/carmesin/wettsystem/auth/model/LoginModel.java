package com.carmesin.wettsystem.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
/**
 * Create the LoginUser Object
 */
public class LoginModel {

    private String user;
    private String password;

}


