package de.applegreen.household.business.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Alexander Tepe | a.tepe@kalverkamp.de
 */
@Getter
@Setter
public class ClosingsDTO {

    private String monthString;
    private Integer year;
    private Double probationSophie;
    private Double probationAlex;

    public ClosingsDTO() {}
}
