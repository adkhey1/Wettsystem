package com.carmesin.wettsystem.auth.service;

import com.carmesin.wettsystem.auth.model.UserModel;
import com.carmesin.wettsystem.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * check entries from login
     *
     * @param name exist Username in Database
     * @param password input Password
     * @param model
     * @param response UUID (Cookie for Session)
     * @return wetten = successful login; login = incorrect entries
     */

    public String login(String name, String password, Model model, HttpServletResponse response) {

        if (userNotExist(name)) {
            model.addAttribute("login", "User does not exists");
        } else {
            if (isValidPassword(name, password)) {

                //Create a random UUID
                String uuid = UUID.randomUUID().toString();

                updateUuid(uuid, name);
                Cookie cookie = new Cookie("UUID", uuid);
                //add cookie to response
                response.addCookie(cookie);

                return "redirect:/wetten";  //for progress: redirect
            } else {
                model.addAttribute("login", "Username and Password do not match");
            }
        }
        return "login";
    }

    /**
     * Update UUID when User logging in again
     *
     * @param uuid Random ID
     * @param name exist Username
     */
    private void updateUuid(String uuid, String name) {
        UserModel user = userRepository.findByName(name).get(0);
        userRepository.deleteByName(name); // Just because update is impossible in spring
        user.setUuid(uuid);
        userRepository.save(user);
    }

    /**
     * checks the existence of the user name
     *
     * @param name
     * @return true = user not exists; false = user exists
     */
    public boolean userNotExist(String name) {
        return userRepository.findByName(name).isEmpty();
    }

    /**
     * Validate if passwords are equal
     *
     * @param name input of Username
     * @param password  first input of password
     * @return true = equal passwords / false = unequal passwords
     */
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

