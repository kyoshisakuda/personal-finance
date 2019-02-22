package com.kyoshisakuda.budget.account;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountService {

    private List<Account> accounts = new ArrayList<Account>(Arrays.asList(
            new Account(1, "Caja chica", "Efectivo"),
            new Account(2, "Ahorros BCP", "Cuenta de Ahorros BCP"),
            new Account(3, "Credito BCP", "Tarjeta de crédito BCP")
    ));

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Account getAccount(long id) {
        return accounts.stream().filter(acc -> acc.getId() == id).findFirst().get();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void updateAccount(long id, Account account) {
        for (int i=0; i<accounts.size(); i++) {
            if (accounts.get(i).getId() == id) {
                accounts.set(i, account);
                return;
            }
        }
    }

    public void deleteAccount(long id) {
        accounts.removeIf(acc -> acc.getId() == id);
    }
}
