package com.kyoshisakuda.budget.account;

import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {

    List<Account> getAccounts();
    Account getAccount(int id);
    boolean addAccount(Account account);
    boolean updateAccount(int id, Account account);
    boolean deleteAccount(int id);

}
