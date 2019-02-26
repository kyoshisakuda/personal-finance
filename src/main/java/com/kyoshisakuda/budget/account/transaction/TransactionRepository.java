package com.kyoshisakuda.budget.account.transaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    public List<Transaction> findByAccountId(int id);

}
