package de.applegreen.household.model;

import de.applegreen.household.model.util.ProbationDAO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Closing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime date;

    private int month;

    private Double probationSophie;

    private Double probationAlex;

    @OneToMany
    private Collection<Bill> bills;

    public Closing(ProbationDAO data) {
        this.date = data.getMonth();
        this.probationSophie = data.getProbationSophie();
        this.probationAlex = data.getProbationAlex();
    }

    public Closing() {
        this.date = LocalDateTime.now();
        this.month = this.date.getMonthValue();
        this.bills = new ArrayList<>();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

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

    @Override
    public String toString() {
        return this.date.toString() + " Sophie: " + probationSophie.toString() + " Alex: " + probationAlex.toString();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Collection<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
