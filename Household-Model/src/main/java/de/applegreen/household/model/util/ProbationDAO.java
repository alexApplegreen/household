package de.applegreen.household.model.util;

import java.time.LocalDate;

public class ProbationDAO {

    private Double probationSophie;

    private Double probationAlex;

    private LocalDate month;

    public ProbationDAO() {}

    public Double getProbationSophie() {
        return probationSophie;
    }

    public void setProbationSophie(Double probationSophie) {
        this.probationSophie = probationSophie;
    }

    public Double getProbationAlex() {
        return probationAlex;
    }

    public void setProbationAlex(Double probationAlex) {
        this.probationAlex = probationAlex;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }
}
