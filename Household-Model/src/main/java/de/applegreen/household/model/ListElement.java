package de.applegreen.household.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class ListElement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic
    @Column(name = "element")
    private String element;

    @Basic
    @Column(name = "time_added")
    private LocalDateTime time_added;

    public ListElement() {
        this.time_added = LocalDateTime.now();
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTime_added() {
        return time_added;
    }

    public void setTime_added(LocalDateTime time_added) {
        this.time_added = time_added;
    }
}
