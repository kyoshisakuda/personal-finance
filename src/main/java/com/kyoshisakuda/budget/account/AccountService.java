package com.kyoshisakuda.budget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        repository.findAll().forEach(accounts::add);
        return accounts;
    }

    public Account getAccount(int id) {
        return repository.findById(id).get();
    }

    public void addAccount(Account account) {
        repository.save(account);
    }

    public void updateAccount(int id, Account account) {
        if (repository.findById(id).isPresent())
            repository.save(account);
    }

    public void deleteAccount(int id) {
        repository.deleteById(id);
    }
}
