package com.kyoshisakuda.budget.account.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public List<Transaction> getTransactions(int id) {
        List<Transaction> transactions = new ArrayList<>();
        repository.findByAccountId(id)
                .forEach(transactions::add);
        return transactions;
    }

    public Transaction getTransaction(long id) {
        return repository.findById(id).get();
    }

    public void addTransaction(Transaction transaction) {
        repository.save(transaction);
    }

    public void updateTransaction(long id, Transaction transaction) {
        if (repository.findById(id).isPresent())
            repository.save(transaction);
    }

    public void deleteTransaction(long id) {
        repository.deleteById(id);
    }

}
