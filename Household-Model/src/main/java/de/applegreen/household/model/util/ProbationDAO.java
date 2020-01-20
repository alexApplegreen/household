package de.applegreen.household.model.util;

import java.time.LocalDateTime;

public class ProbationDAO {

    private Double probationSophie;

    private Double probationAlex;

    private LocalDateTime month;

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

    public LocalDateTime getMonth() {
        return month;
    }

    public void setMonth(LocalDateTime month) {
        this.month = month;
    }
}
