package de.applegreen.household.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime date;

    private int month;

    private Double price;

    private boolean payedbySophie;

    private boolean payedByAlex;

    private String user;

    public Bill() {
        this.date = LocalDateTime.now();
        this.month = this.date.getMonthValue();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean isPayedbySophie() {
        return payedbySophie;
    }

    public void setPayedbySophie(boolean payedbySophie) {
        this.payedbySophie = payedbySophie;
    }

    public boolean isPayedByAlex() {
        return payedByAlex;
    }

    public void setPayedByAlex(boolean payedByAlex) {
        this.payedByAlex = payedByAlex;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
