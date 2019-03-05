package com.kyoshisakuda.budget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        return Optional.ofNullable(service.getAccount(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAccount(@RequestBody Account account) {
        if (!service.addAccount(account))
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@RequestBody Account account, @PathVariable int id) {
        try {
            if (!service.updateAccount(id, account))
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException ex) {
            if (ex.getStatus().equals(HttpStatus.NOT_FOUND))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            else if (ex.getStatus().equals(HttpStatus.INTERNAL_SERVER_ERROR))
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            else
                throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable int id) {
        if (!service.deleteAccount(id))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
