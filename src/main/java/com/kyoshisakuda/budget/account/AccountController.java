package com.kyoshisakuda.budget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping
    public List<Account> getAllAccounts() {
        return service.getAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable int id) {
        return service.getAccount(id);
    }

    @PostMapping
    public void addAccount(@RequestBody Account account) {
        service.addAccount(account);
    }

    @PutMapping("/{id}")
    public void updateAccount(@RequestBody Account account, @PathVariable int id) {
        service.updateAccount(id, account);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable int id) {
        service.deleteAccount(id);
    }

}
