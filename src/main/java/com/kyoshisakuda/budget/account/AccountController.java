package com.kyoshisakuda.budget.account;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class AccountController {

    @RequestMapping("/accounts")
    public List<Account> getAllAccounts() {
        return Arrays.asList(
                new Account(1, "Caja chica", "Efectivo"),
                new Account(2, "Ahorros BCP", "Cuenta de Ahorros BCP"),
                new Account(3, "Credito BCP", "Tarjeta de crédito BCP")
        );
    }

}
