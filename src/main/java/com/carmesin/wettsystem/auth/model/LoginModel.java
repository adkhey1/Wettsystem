package com.carmesin.wettsystem.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class LoginModel {

    private String user;
    private String password;

}


