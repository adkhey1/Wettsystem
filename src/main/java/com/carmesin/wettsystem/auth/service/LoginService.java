package com.carmesin.wettsystem.auth.service;

import com.carmesin.wettsystem.auth.model.UserModel;
import com.carmesin.wettsystem.auth.repository.UserRepository;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    public String login(String name, String password, Model model, HttpServletResponse response) {

        if (userNotExist(name)) {
            model.addAttribute("login", "User does not exists");
        } else {
            if (isValidPassword(name, password)) {

                String uuid = UUID.randomUUID().toString();

                updateUuid(uuid, name);
                Cookie cookie = new Cookie("UUID", uuid);
                //add cookie to response
                response.addCookie(cookie);

                return "redirect:/wetten";
            } else {
                model.addAttribute("login", "Username and Password do not match");
            }
        }
        return "login";
    }

    private void updateUuid(String uuid, String name) {
        UserModel user = userRepository.findByName(name).get(0);
        userRepository.deleteByName(name); // Just because update is impossible in spring
        user.setUuid(uuid);
        userRepository.save(user);
    }

    public boolean userNotExist(String name) {
        return userRepository.findByName(name).isEmpty();
    }

    private boolean isValidPassword(String name, String password) {

        List<UserModel> users = userRepository.findByName(name);
        for (UserModel user : users) {
            if(user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }
}

