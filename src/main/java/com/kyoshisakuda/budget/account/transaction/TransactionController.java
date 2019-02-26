package com.kyoshisakuda.budget.account.transaction;

import com.kyoshisakuda.budget.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService service;

    @RequestMapping("/accounts/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable int id) {
        return service.getTransactions(id);
    }

    @RequestMapping("/accounts/{accountId}/transactions/{id}")
    public Transaction getTransaction(@PathVariable long id) {
        return service.getTransaction(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accounts/{accountId}/transactions")
    public void addTransaction(@RequestBody Transaction transaction, @PathVariable int accountId) {
        transaction.setAccount(Account.createEmptyAccountWithId(accountId));
        service.addTransaction(transaction);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/accounts/{accountId}/transactions/{id}")
    public void updateTransaction(@PathVariable int accountId, @PathVariable long id, @RequestBody Transaction transaction) {
        transaction.setAccount(Account.createEmptyAccountWithId(accountId));
        service.updateTransaction(id, transaction);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/accounts/{accountId}/transactions/{id}")
    public void deleteTransaction(@PathVariable long id) {
        service.deleteTransaction(id);
    }

}
