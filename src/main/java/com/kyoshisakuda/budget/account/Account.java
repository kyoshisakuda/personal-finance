package com.kyoshisakuda.budget.account;

import com.kyoshisakuda.budget.Currency;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private double balance;

    public Account() {}

    public Account(String name, String description, Currency currency) {
        this(name, description, currency, 0.0d);
    }

    public Account(String name, String description, Currency currency, double balance) {
        this(0, name, description, currency, balance);
    }

    public Account(int id, String name, String description, Currency currency) {
        this(id, name, description, currency, 0.0d);
    }

    public Account(int id, String name, String description, Currency currency, double balance) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currency = currency;
        this.balance = balance;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
