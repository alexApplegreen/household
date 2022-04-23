package de.applegreen.household.web.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controller to send html pages to client
 * @author Alexander Tepe | a.tepe@kalverkamp.de
 */
@Controller
public class NavigationController {

    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    @GetMapping("/to_list")
    public String toList() {
        return "redirect:/list";
    }

    @GetMapping("/to_closings")
    public String toClosings() {
        return "redirect:/closings";
    }

    @GetMapping("/closings")
    public String closingsPage() {
        return "Closings.html";
    }

    @GetMapping("/list")
    public String listPage() {
        return "List.html";
    }

    @GetMapping("/details/{closing_id}")
    public String detailsPage(@PathVariable("closing_id") String closing_id) {
        // TODO
        return "index.html";
    }
}
