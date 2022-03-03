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

//    MongoClient client = new MongoClient("localhost",27017);
//    MongoDatabase db = client.getDatabase("myFirstDatabase");
//    MongoCollection<Document> collection = db.getCollection("Users");
    //Document query = new Document();
    //    query.append("_id","test");
    //Document setData = new Document();
    //    setData.append("status", 1).append("instagram.likes", 125);
    //Document update = new Document();
    //update.append("$set", setData);
    //To update single Document
    //    collection.updateOne(query, update);

    @Autowired
    private UserRepository userRepository;

    public String booking(String contact, double amount, String uuid, Model model) {

        UserModel user = userRepository.findByUuid(uuid);

        if (contact.contains("btn_einzahlen")) {

            userRepository.deleteByName(user.getName());
            user.setCredits(user.getCredits() + amount);
            userRepository.save(user);

            model.addAttribute("profil", "Successful Load!");
            return "Successful Load!";

        } else if (contact.contains("btn_abbuchen")) {

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

    public String credits(String uuid, Model model) {

        UserModel user = userRepository.findByUuid(uuid);
        model.addAttribute("profil", "Your Credits: " + Math.round(user.getCredits() * 100.0 )/ 100.0);
        return "Your Credits: " + Math.round(user.getCredits() * 100.0 )/ 100.0;

    }
}
