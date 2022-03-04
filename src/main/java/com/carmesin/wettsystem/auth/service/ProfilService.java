package com.carmesin.wettsystem.auth.service;

import com.carmesin.wettsystem.auth.model.UserModel;
import com.carmesin.wettsystem.auth.repository.UserRepository;
import com.carmesin.wettsystem.bet.model.HorseModel;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
//import com.mongodb.MongoClient;

@Service
public class ProfilService {

    @Autowired
    private UserRepository userRepository;

    /**
     * makes transactions with the credits from the user
     *
     * @param contact radioButton value (btn_einzahlen or btn_abbuchen)
     * @param amount input Credits
     * @param uuid to identify User
     * @param model
     * @return Successful Load! or Successful Withdraw! = Successful transaction
     * @return Check your entries = Wrong entries (no transaction)
     */
    public String booking(String contact, double amount, String uuid, Model model) {

        //Identify User by UUID
        UserModel user = userRepository.findByUuid(uuid);

        if (contact.contains("btn_einzahlen")) {
            //load money

            //Update new Credits in Users Collection
            //not a nice method but update MongoDB at SpringBoot does not work
            userRepository.deleteByName(user.getName());
            user.setCredits(user.getCredits() + amount);
            userRepository.save(user);

            model.addAttribute("profil", "Successful Load!");
            return "Successful Load!";

        } else if (contact.contains("btn_abbuchen"))  {
            //debit money

            userRepository.deleteByName(user.getName());
            user.setCredits(user.getCredits() - amount);
            userRepository.save(user);

            model.addAttribute("profil", "Successful Withdraw!");
            return "Successful Withdraw!";

        } else {
            model.addAttribute("profil", "Check your entries");
            return "Check your entries";
        }
    }

    /**
     * Checks account balance of the user
     *
     * @param uuid to identify user
     * @param model
     * @return "Your Credits" + the Credits from the logged in user (Round to 2 digits)
     */
    public String credits(String uuid, Model model) {

        UserModel user = userRepository.findByUuid(uuid);
        model.addAttribute("profil", "Your Credits: " + Math.round(user.getCredits() * 100.0 )/ 100.0);
        return "Your Credits: " + Math.round(user.getCredits() * 100.0 )/ 100.0;

    }
}
