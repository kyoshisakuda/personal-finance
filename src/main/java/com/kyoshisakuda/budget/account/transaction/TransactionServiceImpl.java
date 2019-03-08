package com.kyoshisakuda.budget.account.transaction;

import com.kyoshisakuda.budget.account.Account;
import com.kyoshisakuda.budget.account.AccountRepository;
import org.jetbrains.annotations.NotNull;
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
    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> getTransactions(int id) {
        List<Transaction> transactions = new ArrayList<>();
        repository.findByAccountId(id).forEach(transactions::add);
        return transactions;
    }

    public Transaction getTransaction(long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean addTransaction(Transaction transaction) {
        if (!Optional.ofNullable(repository.save(transaction)).isPresent())
            return false;
        updateAccountBalance(transaction);
        return true;
    }

    public boolean updateTransaction(long id, Transaction transaction) {
        Optional<Transaction> oldTransaction = repository.findById(id);
        if (!oldTransaction.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        transaction.setId(id);
        oldTransaction = Optional.of(oldTransaction.get().clone());
        if (!Optional.ofNullable(repository.save(transaction)).isPresent())
            return false;
        updateAccountBalance(transaction, oldTransaction);
        return true;
    }

    private void updateAccountBalance(Transaction transaction) {
        updateAccountBalance(transaction, Optional.empty());
    }

    private void updateAccountBalance(@NotNull Transaction transaction, @NotNull Optional<Transaction> oldTransaction) {
        Account account = accountRepository.findById(transaction.getAccount().getId()).get();
        if (oldTransaction.isPresent())
            account.rollbackTransaction(oldTransaction.get());
        account.updateBalance(transaction);
        if (!Optional.ofNullable(accountRepository.save(account)).isPresent())
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public boolean deleteTransaction(long id) {
        if (!repository.findById(id).isPresent())
            return true;
        repository.deleteById(id);
        return (!repository.findById(id).isPresent());
    }

}
