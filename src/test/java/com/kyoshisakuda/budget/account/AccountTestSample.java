package com.kyoshisakuda.budget.account;

import com.kyoshisakuda.budget.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountTestSample {

    public static List<Account> getListOfSampleAccounts(boolean isEmpty) {
        if (isEmpty)
            return new ArrayList<>();
        else
            return getListOfSampleAccounts();
    }

    public static List<Account> getListOfSampleAccounts() {
        return Stream.of(
                new Account("AFP", "AFP Habitat", Currency.PEN),
                new Account("Debito Soles", "BCP Cuenta Ahorros Soles", Currency.PEN),
                new Account("Debito Dolares", "BCP Cuenta Ahorros Dolares", Currency.USD),
                new Account("CTS", "CTS BCP", Currency.PEN),
                new Account("Tarjeta de credito", "TC BCP", Currency.PEN))
                .collect(Collectors.toList());
    }

    public static Account getSampleAccount() {
        return new Account(1,"AFP", "AFP Habitaat", Currency.PEN);
    }

}
