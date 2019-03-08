package com.kyoshisakuda.budget.account.transaction;

import com.kyoshisakuda.budget.Currency;
import com.kyoshisakuda.budget.account.Account;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "transactionK")
public class Transaction implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String description;
    private double amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private Category category;
    private LocalDate date;
    private boolean debit;

    @ManyToOne
    private Account account;

    public Transaction() {}

    public Transaction(String description, double amount, Currency currency, int accountId, LocalDate date) {
        this(description, amount, currency, accountId, Category.GENERAL, date);
    }

    public Transaction(String description, double amount, Currency currency, int accountId, Category category, LocalDate date) {
        this((long) 0, description, amount, currency, Account.createEmptyAccountWithId(accountId), category, date);
    }

    public Transaction(String description, double amount, boolean debit, Currency currency, int accountId, Category category, LocalDate date) {
        this((long) 0, description, amount, debit, currency, Account.createEmptyAccountWithId(accountId), category, date);
    }

    public Transaction(Long id, String description, double amount, Currency currency, Account account, Category category, LocalDate date) {
        this(id, description, amount, true, currency, account, category, date);
    }

    public Transaction(Long id, String description, double amount, boolean debit, Currency currency, Account account, Category category, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.debit = debit;
        this.currency = currency;
        this.account = account;
        this.category = category;
        this.date = date;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isDebit() {
        return debit;
    }

    public void setDebit(boolean debit) {
        this.debit = debit;
    }

    @Override
    public Transaction clone() {
        return new Transaction(this.id, this.description, this.amount, this.debit, this.currency, this.account, this.category, this.date);
    }
}
