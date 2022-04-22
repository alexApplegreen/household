package de.applegreen.household.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
public class GroceryList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @Column(name = "products")
    @OneToMany(cascade = CascadeType.ALL)
    private List<ListElement> products;

    @Basic
    @Column(name = "done")
    private Boolean done;

    @Basic
    @Column(name = "current")
    private Boolean current;

    public GroceryList() {
        this.createdOn = LocalDateTime.now();
        this.done = false;
        this.current = false;
        this.products = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ListElement> getProducts() {
        this.products.sort(Comparator.comparing(ListElement::getTime_added));
        return this.products;
    }

    public void setProducts(List<ListElement> products) {
        this.products = products;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }
}
