package com.kyoshisakuda.budget.account.transaction;

import com.kyoshisakuda.budget.account.Account;

import javax.persistence.*;

@Entity(name = "transactionK")
public class Transaction {

    @Id
    private Long id;
    private String description;
    private double amount;
    @ManyToOne
    private Account account;

    public Transaction() {}

    public Transaction(Long id, String description, double amount, int accountId) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.account = Account.createEmptyAccountWithId(accountId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
