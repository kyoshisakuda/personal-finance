package com.kyoshisakuda.budget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return repository.findById(id).orElse(null);
    }

    @Override
    public boolean addAccount(Account account) {
        return Optional.ofNullable(repository.save(account)).isPresent();
    }

    @Override
    public boolean updateAccount(int id, Account account) throws ResponseStatusException {
        if (!repository.findById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        account.setId(id);
        return Optional.ofNullable(repository.save(account)).isPresent();
    }

    @Override
    public boolean deleteAccount(int id) {
        if (!repository.findById(id).isPresent())
            return true;
        repository.deleteById(id);
        return !repository.findById(id).isPresent();
    }
}
