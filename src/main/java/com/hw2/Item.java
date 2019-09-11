package com.hw2;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ITEM")
public class Item {

    private long id;
    private String name;
    private Date dateCreated;
    private Date lastUpdatedDate;
    private String description;

    public Item(String name, Date dateCreated, Date lastUpdatedDate, String description) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.lastUpdatedDate = lastUpdatedDate;
        this.description = description;
    }

    public Item() {
    }



    @JsonProperty("Number")
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "I_SEQ", sequenceName = "ITEM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "I_SEQ")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("Name")
    @Column(name = "ITEM_NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Date Created")
    @Column(name = "DATE_CREATED")
    public Date getDateCreated() {
        return dateCreated;
    }


    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @JsonProperty("Date Updated")
    @Column(name = "DATE_UPDATED")
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @JsonProperty("Description")
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "id=" + id + " " + name + " " + dateCreated + " " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name) &&
                dateCreated.getTime() == item.dateCreated.getTime() &&
                lastUpdatedDate.getTime() == item.lastUpdatedDate.getTime() &&
//                dateCreated.equals(item.dateCreated) &&
//                lastUpdatedDate.equals(item.lastUpdatedDate) &&
                description.equals(item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateCreated, lastUpdatedDate, description);
    }
}
