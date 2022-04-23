package de.applegreen.household.web.controller;

import de.applegreen.household.model.Closing;
import de.applegreen.household.persistence.ClosingRepository;
import de.applegreen.household.web.util.HasLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/closings")
public class ClosingsController implements HasLogger {

    private final ClosingRepository closingRepository;

    @Autowired
    public ClosingsController(ClosingRepository closingRepository) {
        this.closingRepository = closingRepository;
    }

    @GetMapping
    public String showClosings(Model model) {
        List<Closing> closings = this.closingRepository.findAll();
        model.addAttribute("closings", closings);
        return "Closing";
    }

    @GetMapping("/details/{id}")
    public String showDetail(@PathVariable("id") Long id, Model model) {
        Optional<Closing> recent_opt = this.closingRepository.findById(id);
        if (recent_opt.isPresent()) {
            Closing recent = recent_opt.get();
            model.addAttribute("closing", recent);
            model.addAttribute("bills", recent.getBills());
            return "Closing_detail";
        }
        else {
            return "redirect:/";
        }
    }


}
