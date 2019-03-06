package com.kyoshisakuda.budget.account.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kyoshisakuda.budget.Currency;
import com.kyoshisakuda.budget.account.AccountTestSample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionTestSample {

    private static Transaction transaction = new Transaction((long) 1, "Almuerzo", 250.0d, Currency.PEN, AccountTestSample.getSampleAccount());
    private static List<Transaction> transactions = Stream.of(transaction,
                                                new Transaction("Cena", 45.7d, Currency.PEN, AccountTestSample.getSampleAccount().getId()),
                                                new Transaction("Cafe", 3.2d, Currency.PEN, AccountTestSample.getSampleAccount().getId()),
                                                new Transaction("taxi", 8.5d, Currency.PEN, AccountTestSample.getSampleAccount().getId()),
                                                new Transaction("ropa", 70.0d, Currency.PEN, AccountTestSample.getSampleAccount().getId()))
                                            .collect(Collectors.toList());

    public static Transaction getSampleTransaction() {
        return transaction;
    }

    public static List<Transaction> getListOfSampleTransactions() {
        return transactions;
    }

    public static List<Transaction> getEmptyListOfTransactions() {
        return new ArrayList<Transaction>(0);
    }

    public static String getSampleTransactionAsJSONString() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(transaction);
    }

}
