package de.applegreen.household.business.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;

/**
 * @author Alexander Tepe | a.tepe@kalverkamp.de
 */
@Getter
@Setter
public class ClosingsDTO {

    private String monthString;
    private Double probationSophie;
    private Double probationAlex;

    public ClosingsDTO() {}
}
