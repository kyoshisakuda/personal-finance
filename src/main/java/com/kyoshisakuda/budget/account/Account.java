package com.kyoshisakuda.budget.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;

    public Account() {}

    public Account(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private Account(int id) {
        this.id = id;
    }

    /**
     * Factory method to create a new Account object empty with just the given id.
     * This is mainly used for creating a dummy object where you just need the ID
     *
     * @param id Account Id
     * @return new Account object
     */
    public static Account createEmptyAccountWithId(int id) {
        return new Account(id);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
