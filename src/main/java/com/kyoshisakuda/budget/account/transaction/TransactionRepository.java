package com.kyoshisakuda.budget.account.transaction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    public List<Transaction> findByAccountId(int id);

}
