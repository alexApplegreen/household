package de.applegreen.household.model;

import de.applegreen.household.model.util.ProbationDAO;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Entity
public class Closing {

    private static final String DECIMALPATTERN = "#.##";

    private static DecimalFormat format = new DecimalFormat(DECIMALPATTERN);

    private static NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMANY);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;

    private int month;

    private Double probationSophie;

    private Double probationAlex;

    private Double probationCombined;

    @OneToMany
    private Collection<Bill> bills;

    public Closing(ProbationDAO data) {
        this.date = data.getMonth();
        this.probationSophie = data.getProbationSophie();
        this.probationAlex = data.getProbationAlex();
        this.probationCombined = Math.max(this.probationAlex, this.probationSophie) / 2;
    }

    public Closing() {
        this.date = LocalDate.now();
        this.month = this.date.getMonthValue();
        this.bills = new ArrayList<>();
        this.probationSophie = 0d;
        this.probationAlex = 0d;
        this.probationCombined = 0d;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getProbationSophie() {
        return probationSophie;
    }

    public void setProbationSophie(Double probationSophie) throws ParseException {
        this.probationSophie = numberFormat.parse(format.format(probationSophie)).doubleValue();
    }

    public Double getProbationAlex() {
        return probationAlex;
    }

    public void setProbationAlex(Double probationAlex) throws ParseException {
        this.probationAlex = numberFormat.parse(format.format(probationAlex)).doubleValue();
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

    public Double getProbationCombined() {
        return probationCombined;
    }

    public void setProbationCombined(Double probationCombined) throws ParseException {
        this.probationCombined = numberFormat.parse(format.format(probationCombined)).doubleValue();
    }
}
