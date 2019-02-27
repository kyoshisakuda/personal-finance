package com.kyoshisakuda.budget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        repository.findAll().forEach(accounts::add);
        return accounts;
    }

    @Override
    public Account getAccount(int id) {
        return repository.findById(id).get();
    }

    @Override
    public void addAccount(Account account) {
        repository.save(account);
    }

    @Override
    public void updateAccount(int id, Account account) {
        if (repository.findById(id).isPresent())
            repository.save(account);
    }

    @Override
    public void deleteAccount(int id) {
        repository.deleteById(id);
    }
}
