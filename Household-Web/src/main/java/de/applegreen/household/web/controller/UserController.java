package de.applegreen.household.web.controller;

import de.applegreen.household.model.User;
import de.applegreen.household.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author [ATE] Alexander Tepe | alexander.tepe@lmis.de
 **/
@Controller
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup/new")
    public String newUser(@ModelAttribute("user") User user) {
        if (!StringUtils.isEmpty(user.getPassword()) && !StringUtils.isEmpty(user.getUsername())) {
            this.userRepository.save(user);
            return "index";
        }
        else {
            return "redirect:/signup";
        }
    }
}
