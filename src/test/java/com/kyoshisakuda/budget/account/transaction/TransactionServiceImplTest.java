package com.kyoshisakuda.budget.account.transaction;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.kyoshisakuda.budget.account.AccountTestSample.*;
import static com.kyoshisakuda.budget.account.transaction.TransactionTestSample.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {

    @TestConfiguration
    static class TransactionServiceTestContextConfiguration {
        @Bean
        public TransactionService transactionService() {
            return new TransactionServiceImpl();
        }
    }

    @Autowired
    private TransactionService service;

    @MockBean
    private TransactionRepository repository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getTransactions_returnListOfTransactions() {
        when(repository.findByAccountId(getSampleAccount().getId())).thenReturn(getListOfSampleTransactions());
        List<Transaction> transactions = service.getTransactions(getSampleAccount().getId());
        assertEquals(getListOfSampleTransactions(), transactions);
    }

    @Test
    public void getTransactions_whenNoTransactions_returnEmptyList() {
        when(repository.findByAccountId(getSampleAccount().getId())).thenReturn(getEmptyListOfTransactions());
        List<Transaction> transactions = service.getTransactions(getSampleAccount().getId());
        assertTrue(transactions.isEmpty());
    }

    @Test
    public void getTransactions_whenAccountNotExist_throwNotFound() {
        when(repository.findByAccountId(getSampleAccount().getId())).thenReturn(getEmptyListOfTransactions());
        List<Transaction> transactions = service.getTransactions(getSampleAccount().getId());
        assertTrue(transactions.isEmpty());
    }

    @Test
    public void getTransaction_returnTransaction() {
        when(repository.findById(getSampleTransaction().getId())).thenReturn(Optional.of(getSampleTransaction()));
        Transaction transaction = service.getTransaction(getSampleTransaction().getId());
        assertEquals(getSampleTransaction(), transaction);
    }

    @Test
    public void getTransaction_whenNotExist_returnNull() {
        when(repository.findById(getSampleTransaction().getId())).thenReturn(Optional.empty());
        Transaction transaction = service.getTransaction(getSampleTransaction().getId());
        assertNull(transaction);
    }

    @Test
    public void addTransaction_returnTrue() {
        when(repository.save(getSampleTransaction())).thenReturn(getSampleTransaction());
        assertTrue(service.addTransaction(getSampleTransaction()));
    }

    @Test
    public void addTransaction_whenErrorSaving_returnFalse() {
        when(repository.save(getSampleTransaction())).thenReturn(null);
        assertFalse(service.addTransaction(getSampleTransaction()));
    }

    @Test
    public void updateTransaction_returnTrue() {
        when(repository.findById(getSampleTransaction().getId())).thenReturn(Optional.of(getSampleTransaction()));
        when(repository.save(getSampleTransaction())).thenReturn(getSampleTransaction());
        assertTrue(service.updateTransaction(getSampleTransaction().getId(), getSampleTransaction()));
    }

    @Test
    public void updateTransaction_whenErrorSaving_returnFalse() {
        when(repository.findById(getSampleTransaction().getId())).thenReturn(Optional.of(getSampleTransaction()));
        when(repository.save(getSampleTransaction())).thenReturn(null);
        assertFalse(service.updateTransaction(getSampleTransaction().getId(), getSampleTransaction()));
    }

    @Test
    public void updateTransaction_whenNotExist_throwNotFound() {
        expectedException.expect(ResponseStatusException.class);
        expectedException.expectMessage(CoreMatchers.is("404 NOT_FOUND"));

        when(repository.findById(getSampleTransaction().getId())).thenReturn(Optional.empty());
        when(repository.findById(getSampleTransaction().getId())).thenReturn(Optional.empty());
        service.updateTransaction(getSampleTransaction().getId(), getSampleTransaction());
    }

    @Test
    public void deleteTransaction_returnTrue() {
        doNothing().when(repository).deleteById(getSampleTransaction().getId());
        when(repository.findById(getSampleTransaction().getId())).thenReturn(Optional.empty());
        assertTrue(service.deleteTransaction(getSampleTransaction().getId()));
    }

    @Test
    public void deleteTransaction_whenErrorDeleting_returnFalse() {
        doNothing().when(repository).deleteById(getSampleTransaction().getId());
        when(repository.findById(getSampleTransaction().getId())).thenReturn(Optional.of(getSampleTransaction()));
        assertFalse(service.deleteTransaction(getSampleTransaction().getId()));
    }

    @Test
    public void deleteTransaction_whenTransactionNotExist_returnTrue() {
        doNothing().when(repository).deleteById(getSampleTransaction().getId());
        when(repository.findById(getSampleTransaction().getId())).thenReturn(Optional.empty());
        assertTrue(service.deleteTransaction(getSampleTransaction().getId()));
    }
}