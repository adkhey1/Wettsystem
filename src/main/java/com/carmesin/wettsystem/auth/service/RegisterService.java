package com.carmesin.wettsystem.auth.service;

import com.carmesin.wettsystem.auth.model.UserModel;
import com.carmesin.wettsystem.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class RegisterService {

    @Autowired
    UserRepository userRepository;

    /**
     *
     * @param user all input for UserModel
     * @param password2 input password (second Password)
     * @param model
     * @param response add cookie
     * @return register = incorrect entries / redirect:wetten = successful regist
     */

    public String registrieren(UserModel user, String password2, Model model,
                               HttpServletResponse response) {

        //for each different case, an if method is used to see what happens.

        //username is empty
        if (user.getName().isEmpty()) {
            model.addAttribute("register", "Please enter a Username");
        }

        //user with the same name already exists
        if (userExist(user.getName())) {
            model.addAttribute("register", "Username always exists");
            return "register";
        }

        //validate E-Mail
        if (!isEmail(user.getEmail())) {
            model.addAttribute("register", "Check your Email -> it needs an '@' and an '.'");
            return "register";
        }

        //validate IBAN
        if (!isIBAN(user.getIBAN())) {
            model.addAttribute("register", "Check your IBAN -> it starts with 'DE' and needs 20 figures");
            return "register";
        }

        //Passwords are similar
        if (isSimilarPassword(user.getPassword(), password2)) {
            addUser(user);
            Cookie cookie = new Cookie("UUID", user.getUuid());
            //add cookie to response
            response.addCookie(cookie);
            return "redirect:/wetten";
        } else {
            model.addAttribute("register", "Your passwords were not identical!");
        }
        return "register";
    }

    /**
     * User get saved in repository
     *
     * @param user insert into databases
     */
    private void addUser(UserModel user) {
        userRepository.save(user);
    }

    /**
     * Check if user exists in User table
     *
     * @param name to search for
     * @return true = user exists / false = user does not exist
     */
    public boolean userExist(String name) {
        return !userRepository.findByName(name).isEmpty();
    }

    /**
     * Validate if passwords are equal
     *
     * @param password  first input of password
     * @param password2 second input of password
     * @return true = equal passwords / false = unequal passwords
     */
    private boolean isSimilarPassword(String password, String password2) {
        return password.equals(password2);
    }

    /**
     * Validate email address
     *
     * @param email to validate
     * @return true = valid email / false = invalid email
     */
    private boolean isEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    /**
     * Validate iban
     *
     * @param iban to validate
     * @return true = valid iban / false = invalid iban
     */
    private boolean isIBAN(String iban) {
        return iban.startsWith("DE") && iban.length() == 22;
    }
}
