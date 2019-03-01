package com.kyoshisakuda.budget.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.kyoshisakuda.budget.account.AccountTestSample.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AccountServiceImplTest {

    @TestConfiguration
    static class AccountServiceTestContextConfiguration {
        @Bean
        public AccountService accountService() {
            return new AccountServiceImpl();
        }
    }

    @Autowired
    private AccountService service;

    @MockBean
    private AccountRepository repository;

    @Test
    public void getAccounts_returnAllAccounts() {
        Mockito.when(repository.findAll()).thenReturn(getListOfSampleAccounts());
        List<Account> accounts = service.getAccounts();
        assertTrue(accounts.size()==getListOfSampleAccounts().size());
        for (int i=0; i<accounts.size(); i++)
            assertEquals(accounts.get(i), getListOfSampleAccounts().get(i));
    }

    @Test
    public void getAccounts_whenEmpty_returnEmpty() {
        Mockito.when(repository.findAll()).thenReturn(getListOfSampleAccounts(true));
        assertTrue(service.getAccounts().isEmpty());
    }

    @Test
    public void getAccount_returnAccount() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(getSampleAccount()));
        Account account = service.getAccount(1);
        assertTrue(account != null);
        assertEquals(account, getSampleAccount());
    }

    @Test
    public void getAccount_whenNotExist_returnNull() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());
        Account account = service.getAccount(1);
        assertTrue(account == null);
    }

    @Test
    public void addAccount_thenOK() {
        Mockito.when(repository.save(getSampleAccount())).thenReturn(getSampleAccount());
        assertTrue(service.addAccount(getSampleAccount()));
    }

    @Test
    public void addAccount_thenErrorSaving() {
        Mockito.when(repository.save(getSampleAccount())).thenReturn(null);
        assertFalse(service.addAccount(getSampleAccount()));
    }

    @Test
    public void updateAccount_thenOK() {
        Mockito.when(repository.findById(getSampleAccount().getId())).thenReturn(Optional.of(getSampleAccount()));
        Mockito.when(repository.save(getSampleAccount())).thenReturn(getSampleAccount());
        assertTrue(service.updateAccount(getSampleAccount().getId(), getSampleAccount()));
    }

    @Test
    public void updateAccount_thenErrorSaving() {
        Mockito.when(repository.findById(getSampleAccount().getId())).thenReturn(Optional.of(getSampleAccount()));
        Mockito.when(repository.save(getSampleAccount())).thenReturn(null);
        assertFalse(service.updateAccount(getSampleAccount().getId(), getSampleAccount()));
    }

    @Test
    public void updateAccount_whenIdNotExist_returnFalse() {
        Mockito.when(repository.findById(getSampleAccount().getId())).thenReturn(Optional.empty());
        Mockito.when(repository.save(getSampleAccount())).thenReturn(getSampleAccount());
        assertFalse(service.updateAccount(getSampleAccount().getId(), getSampleAccount()));
    }

    @Test
    public void deleteAccount_thenDeleted() {
        Mockito.doNothing().when(repository).deleteById(getSampleAccount().getId());
        Mockito.when(repository.findById(getSampleAccount().getId())).thenReturn(Optional.empty());
        assertTrue(service.deleteAccount(getSampleAccount().getId()));
    }

    @Test
    public void deleteAccount_thenErrorDeleting() {
        Mockito.doNothing().when(repository).deleteById(getSampleAccount().getId());
        Mockito.when(repository.findById(getSampleAccount().getId())).thenReturn(Optional.of(getSampleAccount()));
        assertFalse(service.deleteAccount(getSampleAccount().getId()));
    }
}