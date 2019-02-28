package com.kyoshisakuda.budget.account;

import com.kyoshisakuda.budget.Currency;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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

    private Account afp;

    @Before
    public void setUp() throws Exception {
        afp = new Account("AFP", "AFP Habitat", Currency.PEN);
        List<Account> accounts = new ArrayList<>(1);
        accounts.add(afp);
        Mockito.when(repository.findAll()).thenReturn(accounts);
    }

    @Test
    public void findAllTest() {
        List<Account> accounts = new ArrayList<>();
        repository.findAll().forEach(accounts::add);
        assertTrue(accounts.size()==1);
        assertTrue(accounts.get(0) == afp);
    }
}