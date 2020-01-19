package de.applegreen.household.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InformationController {

    @GetMapping("/alive")
    public ResponseEntity alive() {
        return ResponseEntity.ok().build();
    }
}
