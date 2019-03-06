package com.kyoshisakuda.budget.account.transaction;

import com.kyoshisakuda.budget.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping("/accounts/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable int id) {
        return service.getTransactions(id);
    }

    @GetMapping("/accounts/{accountId}/transactions/{id}")
    public Transaction getTransaction(@PathVariable long id) {
        return Optional.ofNullable(service.getTransaction(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/accounts/{accountId}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransaction(@RequestBody Transaction transaction, @PathVariable int accountId) {
        transaction.setAccount(Account.createEmptyAccountWithId(accountId));
        if (!service.addTransaction(transaction))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/accounts/{accountId}/transactions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTransaction(@PathVariable int accountId, @PathVariable long id, @RequestBody Transaction transaction) {
        transaction.setAccount(Account.createEmptyAccountWithId(accountId));
        if (!service.updateTransaction(id, transaction))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/accounts/{accountId}/transactions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable long id) {
        if (!service.deleteTransaction(id))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
