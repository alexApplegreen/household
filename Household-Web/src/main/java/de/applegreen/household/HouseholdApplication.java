package de.applegreen.household;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO add JPA Auditing for creation dates?
@SpringBootApplication
public class HouseholdApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseholdApplication.class, args);
    }
}
