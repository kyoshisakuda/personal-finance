package com.kyoshisakuda.budget.account;

import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {

    List<Account> getAccounts();
    Account getAccount(int id);
    void addAccount(Account account);
    void updateAccount(int id, Account account);
    void deleteAccount(int id);

}
