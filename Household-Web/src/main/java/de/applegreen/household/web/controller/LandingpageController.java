package de.applegreen.household.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingpageController {

    @GetMapping("/")
    public String home() {
        return "Index";
    }

    @GetMapping("/to_list")
    public String toList() {
        return "redirect:/list";
    }

    @GetMapping("/to_closings")
    public String toClosings() {
        return "redirect:/closings";
    }
}
