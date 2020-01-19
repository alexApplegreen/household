package de.applegreen.household.model.util;

import java.time.LocalDateTime;

public class ProbationDAO {

    private Long probationSophie;

    private Long probationAlex;

    private LocalDateTime month;

    public ProbationDAO() {}

    public Long getProbationSophie() {
        return probationSophie;
    }

    public void setProbationSophie(Long probationSophie) {
        this.probationSophie = probationSophie;
    }

    public Long getProbationAlex() {
        return probationAlex;
    }

    public void setProbationAlex(Long probationAlex) {
        this.probationAlex = probationAlex;
    }

    public LocalDateTime getMonth() {
        return month;
    }

    public void setMonth(LocalDateTime month) {
        this.month = month;
    }
}
