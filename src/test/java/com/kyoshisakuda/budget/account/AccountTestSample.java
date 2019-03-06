package com.kyoshisakuda.budget.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kyoshisakuda.budget.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountTestSample {

    private static List<Account> accounts = Stream.of(
            new Account("AFP", "AFP Habitat", Currency.PEN),
            new Account("Debito Soles", "BCP Cuenta Ahorros Soles", Currency.PEN),
            new Account("Debito Dolares", "BCP Cuenta Ahorros Dolares", Currency.USD),
            new Account("CTS", "CTS BCP", Currency.PEN),
            new Account("Tarjeta de credito", "TC BCP", Currency.PEN))
            .collect(Collectors.toList());

    private static Account account_AFP = new Account(1,"AFP", "AFP Habitat", Currency.PEN);

    public static List<Account> getListOfSampleAccounts(boolean isEmpty) {
        if (isEmpty)
            return new ArrayList<>();
        else
            return getListOfSampleAccounts();
    }

    public static List<Account> getListOfSampleAccounts() {
        return accounts;
    }

    public static Account getSampleAccount() {
        return account_AFP;
    }

    public static String getAccountAsJSONString() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(account_AFP);
    }

}
