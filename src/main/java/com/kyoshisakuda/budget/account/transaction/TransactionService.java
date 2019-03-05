package com.kyoshisakuda.budget.account.transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions(int id);
    Transaction getTransaction(long id);
    boolean addTransaction(Transaction transaction);
    boolean updateTransaction(long id, Transaction transaction);
    boolean deleteTransaction(long id);

}
