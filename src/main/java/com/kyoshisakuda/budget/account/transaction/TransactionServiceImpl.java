package com.kyoshisakuda.budget.account.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

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

    public boolean addTransaction(Transaction transaction) {
        return Optional.ofNullable(repository.save(transaction)).isPresent();
    }

    public boolean updateTransaction(long id, Transaction transaction) throws ResponseStatusException {
        if (!repository.findById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return Optional.ofNullable(repository.save(transaction)).isPresent();
    }

    public boolean deleteTransaction(long id) {
        repository.deleteById(id);
        return (!repository.findById(id).isPresent());
    }

}
