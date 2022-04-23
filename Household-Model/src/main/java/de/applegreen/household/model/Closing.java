package de.applegreen.household.model;

import de.applegreen.household.model.util.ProbationDAO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Entity
public class Closing {

    private static final String DECIMALPATTERN = "#.##";

    private static final DecimalFormat format = new DecimalFormat(DECIMALPATTERN);

    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMANY);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "date")
    private LocalDate date;

    @Basic
    @Column(name = "month")
    private int month;

    @Basic
    @Column(name = "year")
    private int year;

    @Basic
    @Column(name = "monthString")
    private String monthString;

    @Basic
    @Column(name = "probationSophie")
    private Double probationSophie;

    @Basic
    @Column(name = "probationAlex")
    private Double probationAlex;

    @Basic
    @Column(name = "probationCombined")
    private Double probationCombined;

    @OneToMany
    @Column(name = "bills")
    private Collection<Bill> bills;

    @Deprecated
    public Closing(ProbationDAO data) {
        this.date = data.getMonth();
        this.probationSophie = data.getProbationSophie();
        this.probationAlex = data.getProbationAlex();
        this.probationCombined = Math.max(this.probationAlex, this.probationSophie) / 2;
    }

    public Closing() {
        this.date = LocalDate.now();
        this.month = this.date.getMonthValue();
        this.year = this.date.getYear();
        this.monthString = this.date.getMonth().name() + " " + this.date.getYear();
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

    public String getMonthString() {
        return monthString;
    }

    public void setMonthString(String monthString) {
        this.monthString = monthString;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
