package com.kyoshisakuda.budget.account.transaction;

import com.kyoshisakuda.budget.Currency;
import com.kyoshisakuda.budget.account.Account;

import javax.persistence.*;

@Entity(name = "transactionK")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String description;
    private double amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    private Account account;

    public Transaction() {}

    public Transaction(String description, double amount, Currency currency, int accountId) {
        this((long) 0, description, amount, currency, Account.createEmptyAccountWithId(accountId));
    }

    public Transaction(Long id, String description, double amount, Currency currency, Account account) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
